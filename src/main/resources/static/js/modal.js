class PlaceNum {
    #value;

    constructor() {
        this.#value = 1;
    }

    value() {
        return this.#value;
    }

    increment() {
        this.#value ++;
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
            console.error(error);
            return '';
        }
    }

    addFragment() {
        if (this.#value === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
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
    #modal;

    constructor() {
        const modalElement = document.getElementById(`placeModal${placeNum.value()}`);
        const toggleBtn = document.getElementById(`placeToggleBtn${placeNum.value()}`);
        const closeBtn = document.getElementById(`placeClose${placeNum.value()}`);
        const inputElement = document.getElementById(`place${placeNum.value()}`);

        if (!modalElement || !toggleBtn || !closeBtn || !inputElement) { return; }
        this.#modal = new Modal(modalElement);

        toggleBtn.addEventListener('click', () => this.#modal.toggle());
        closeBtn.addEventListener('click', () => {
            this.#modal.hide();
            document.activeElement.blur();
        });
        // eslint-disable-next-line no-undef
        const autoComplete = new AutoComplete(inputElement);
        // eslint-disable-next-line no-undef
        autoCompleteList.add(autoComplete);
    }

    closeModal() {
        this.#modal.hide();
    }
}

class ModalForm {
    #formElement;

    constructor() {
        this.#formElement = document.getElementById(`placeForm${placeNum.value()}`);
        if (!this.#formElement) { return; }

        this.#formElement.addEventListener('submit', async(e) => {
            e.preventDefault();

            const fragment = new Fragment();
            await fragment.initialize();

            if (!fragment.value()) { return; }

            const modal = new ModalElement();

            fragment.addFragment();
            modal.closeModal();

            placeNum.increment();
            new ModalElement();
            new ModalForm();
        });
    }
}

const placeNum = new PlaceNum();

new ModalForm();
