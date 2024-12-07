class PlaceNum {
    #value = 1;

    value() {
        return this.#value;
    }

    increment() {
        this.#value ++;
    }
}

class SessionStorageList {
    #startPlaceList = new Array(0);
    #endPlaceList = new Array(0);
    #placesList = new Array(0);

    setStartPlace() {
        this.#startPlaceList = [];
        this.#startPlaceList.push({
            placeId: document.getElementById('startPlaceId').value,
            lat: document.getElementById('startLat').value,
            lng: document.getElementById('startLng').value,
            name: document.getElementById('startPlace').value,
            startTime: document.getElementById('startTime').value
        });

        sessionStorage.setItem('startPlace', JSON.stringify(this.#startPlaceList));
    }

    setEndPlace() {
        this.#endPlaceList = [];
        this.#endPlaceList.push({
            placeId: document.getElementById('endPlaceId').value,
            lat: document.getElementById('endLat').value,
            lng: document.getElementById('endLng').value,
            name: document.getElementById('endPlace').value
        });

        sessionStorage.setItem('endPlace', JSON.stringify(this.#endPlaceList));
    }

    setPlaces() {
        this.#placesList.push({
            placeId: document.getElementById(`placeId${placeNum.value()}`).value,
            lat: document.getElementById(`placeLat${placeNum.value()}`).value,
            lng: document.getElementById(`placeLng${placeNum.value()}`).value,
            name: document.getElementById(`place${placeNum.value()}`).value,
            budget: document.getElementById(`budget${placeNum.value()}`).value,
            stayTime: document.getElementById(`stayTime${placeNum.value()}`).value,
            desiredStartTime: document.getElementById(`desiredStartTime${placeNum.value()}`).value,
            desiredEndTime: document.getElementById(`desiredEndTime${placeNum.value()}`).value,
        });

        sessionStorage.setItem('place', JSON.stringify(this.#placesList));
    }
}

class Fragment {
    #value;

    constructor() {
        this.#value = null;
    }

    async initialize() {
        try {
            const response = await fetch(`/fragment/modal/places?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#value = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    addFragment() {
        if (this.#value === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
        // id=destination の子要素に追加
        const container = document.getElementById('destination');
        const item = document.createElement('div');
        item.innerHTML = this.#value;
        container.appendChild(item);
    }

    value() {
        if (this.#value === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
        return this.#value;
    }
}

class ModalElement {
    #modals;
    #toggleButtons;
    #closeButtons;

    constructor() {
        this.#modals = {
            start: document.getElementById('startModal'),
            end: document.getElementById('endModal'),
            places: [document.getElementById(`placeModal${placeNum.value()}`)],
        };

        this.#toggleButtons = {
            start: document.getElementById('startToggle'),
            end: document.getElementById('endToggle'),
            places: [document.getElementById(`placeToggleBtn${placeNum.value()}`)],
        };

        this.#closeButtons = {
            start: document.getElementById('startClose'),
            end: document.getElementById('endClose'),
            places: [document.getElementById(`placeCloseBtn${placeNum.value()}`)],
        };
    }

    addPlacesElement() {
        this.#modals.places.push(document.getElementById(`placeModal${placeNum.value()}`));
        this.#toggleButtons.places.push(document.getElementById(`placeToggleBtn${placeNum.value()}`));
        this.#closeButtons.places.push(document.getElementById(`placeCloseBtn${placeNum.value()}`));
        this.addButtonEvent('places');

        const inputElement = document.getElementById(`place${placeNum.value()}`);
        const autoComplete = new AutoComplete(inputElement);
        autoCompleteList.add(autoComplete);
    }

    /**
     * ModalButtonイベント アタッチ
     * @param modalType (start,end,places)が入る
     */
    addButtonEvent(modalType) {
        if (modalType === 'places') {
            const modal = new Modal(this.#modals[modalType][placeNum.value()-1]);
            this.#toggleButtons.places[placeNum.value()-1].addEventListener('click', () => modal.toggle());
            this.#closeButtons.places[placeNum.value()-1].addEventListener('click', () => {
                modal.hide();
                document.activeElement.blur(); // フォーカスを外す
            });
            return;
        }
        const modal = new Modal(this.#modals[modalType]);
        this.#toggleButtons[modalType].addEventListener('click', () => modal.toggle());
        this.#closeButtons[modalType].addEventListener('click', () => {
            modal.hide();
            document.activeElement.blur(); // フォーカスを外す
        });
    }

    closeModal(modalType) {
        if (modalType === 'places') {
            const modal = new Modal(this.#modals[modalType][placeNum.value()-1]);
            modal.hide();
            return;
        }
        const modal = new Modal(this.#modals[modalType]);
        modal.hide();
    }
}

class ModalForm {
    #startFormElement;
    #placeFormElement;
    #endFormElement;

    constructor() {
        this.#startFormElement = document.getElementById('startPlaceForm');
        this.#placeFormElement = document.getElementById(`placeForm${placeNum.value()}`);
        this.#endFormElement = document.getElementById('endPlaceForm');
        this.initFormEvent();
    }

    initFormEvent() {
        if (!this.#startFormElement || !this.#placeFormElement || !this.#endFormElement) return;
        this.#startFormElement.addEventListener('submit', async(e) => {
            e.preventDefault();

            sessionStorageList.setStartPlace();

            const modalType = 'start';
            modal.closeModal(modalType);
            modal.addButtonEvent(modalType);
        });
        this.#endFormElement.addEventListener('submit', async(e) => {
            e.preventDefault();

            sessionStorageList.setEndPlace();

            const modalType = 'end';
            modal.closeModal(modalType);
            modal.addButtonEvent(modalType);
        });
        this.addPlaceEvent();
    }

    addPlaceEvent() {
        const modalType = 'places';
        this.#placeFormElement.addEventListener('submit', async(e) => {
            e.preventDefault();

            sessionStorageList.setPlaces();

            modal.closeModal(modalType);
            modal.addButtonEvent(modalType);

            // 追加するフラグメントの呼び出し
            const fragment = new Fragment();
            await fragment.initialize();
            if (!fragment.value()) return;

            fragment.addFragment();

            // 追加したフラグメントに対して実行
            placeNum.increment();
            modal.addPlacesElement();
            new ModalForm();
        });
    }
}

const placeNum = new PlaceNum();
const sessionStorageList = new SessionStorageList();
const modal = new ModalElement();

new ModalForm();
