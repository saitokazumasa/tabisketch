class RadioButton {
    #element;
    #svgElement;
    #parentList;

    constructor(element, svgElement, parentList) {
        this.#element = element;
        this.#svgElement = svgElement;
        this.#parentList = parentList;
        this.#element.addEventListener('change', () => this.onChanged());
        this.onChanged();
    }

    onChanged() {
        this.#parentList.getAllButtons().forEach((radioButton) => {
            if (!radioButton.#element.checked) {
                radioButton.#svgElement.classList.add('fill-label');
                return;
            }
            radioButton.#svgElement.classList.remove('fill-label');
        });
    }
}

class RadioButtonList {
    #values = [];

    constructor(radioGroupName, svgClass) {
        const radioButtonElements = document.querySelectorAll(`input[name="${radioGroupName}"]`);
        const svgElements = document.querySelectorAll(`.${svgClass}`);

        if (radioButtonElements.length <= 0 || svgElements.length <= 0) return;

        radioButtonElements.forEach((radio, index) => {
            const radioButton = new RadioButton(radio, svgElements[index], this);
            this.#values.push(radioButton);
        });
    }

    getAllButtons() {
        return this.#values;
    }
}
const radioButtonList = new RadioButtonList('fourTransportation', 'fill-label');
