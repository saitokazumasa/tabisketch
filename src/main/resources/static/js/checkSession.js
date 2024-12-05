const checkbox = document.getElementById('交通機関を利用するまでの徒歩時間flg');
const inputField = document.getElementById('交通機関を利用するまでの徒歩時間');

checkbox.addEventListener('change', () => {
    if (checkbox.checked) {
        inputField.disabled = false;
    } else {
        inputField.disabled = true;
    }
});

// TODO: 適切なセッションキーを設定する
document.addEventListener('DOMContentLoaded', function() {
    const session = {
        startPoint: null,
        destination: null,
        endPoint: null
    };

    const messageElement = document.querySelector('.text-danger.my-4');

    function checkInputs() {
        const isStartPointEntered = session.startPoint && session.startPoint.trim() !== '';
        const isDestinationEntered = session.destination && session.destination.trim() !== '';
        const isEndPointEntered = session.endPoint && session.endPoint.trim() !== '';

        if (isStartPointEntered || isDestinationEntered || isEndPointEntered) {
            messageElement.style.display = 'none';
        } else {
            messageElement.style.display = 'block';
        }
    }

    checkInputs();

    // セッションデータの更新処理（例: ボタンや入力イベントに応じて）
    document.querySelectorAll('input[type="text"]').forEach(input => {
        input.addEventListener('input', function() {
            const id = input.getAttribute('id');

            if (id === 'startPoint') {
                session.startPoint = input.value;
            } else if (id === 'destination') {
                session.destination = input.value;
            } else if (id === 'endPoint') {
                session.endPoint = input.value;
            }

            checkInputs();
        });
    });
});
