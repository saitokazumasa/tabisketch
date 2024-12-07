class SessionToken {
    #value;

    constructor() {
        this.#value = new google.maps.places.AutocompleteSessionToken();
    }

    value() {
        return this.#value;
    }
}

/**
 * Google の AutoComplete API（場所検索のサジェスト機能）
 */
class AutoComplete {
    #value;
    #inputElement;

    constructor(inputElement) {
        this.#inputElement = inputElement;

        this.#value =
            new google.maps.places.Autocomplete(inputElement, {
                types: ['establishment'],
                componentRestrictions: {country: ['JP']},
                fields: ['place_id', 'geometry', 'name'],
            });

        const sessionToken = new SessionToken();
        this.#value.setOptions({sessionToken: sessionToken.value()});
        this.#value.addListener('place_changed', () => this.onPlaceChanged());
    }

    onPlaceChanged() {
        const place = this.#value.getPlace();

        if (!place.geometry) {
            // 確認用です
            this.#inputElement.placeholder = '場所を入力して下さい';
            return;
        }
        this.#inputElement.value = place.name;

        const sessionToken = new SessionToken();
        this.#value.setOptions({sessionToken: sessionToken.value()});

        // placeId, lat, lng の更新
        if (this.#inputElement.id === initInstanceKeys[0]) {
            document.getElementById('startPlaceId').value = place.place_id;
            document.getElementById('startLat').value = place.geometry.location.lat();
            document.getElementById('startLng').value = place.geometry.location.lng();
            return;
        }
        if (this.#inputElement.id === initInstanceKeys[1]) {
            document.getElementById('endPlaceId').value = place.place_id;
            document.getElementById('endLat').value = place.geometry.location.lat();
            document.getElementById('endLng').value = place.geometry.location.lng();
            return;
        }
        document.getElementById(`placeId${placeNum.value()}`).value = place.place_id;
        document.getElementById(`placeLat${placeNum.value()}`).value = place.geometry.location.lat();
        document.getElementById(`placeLng${placeNum.value()}`).value = place.geometry.location.lng();
    }
}

class AutoCompleteList {
    #value = new Array(0);

    add(value) {
        this.#value.push(value);
    }
}

const initInstanceKeys = [
    'startPlace',
    'endPlace',
    'place1'
];

const autoCompleteList = new AutoCompleteList();

function initAutoComplete() {
    initInstanceKeys.forEach(key => {
        const inputElement = document.getElementById(key);
        if (!inputElement) return;

        const autoComplete = new AutoComplete(inputElement);
        autoCompleteList.add(autoComplete);
    });
}
