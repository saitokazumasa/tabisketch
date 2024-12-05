function displayMap(elementId) {
    new google.maps.Map(document.getElementById(elementId), {
        // TODO:バック側で指定位置のポイントが取得できれば、その位置を初期位置として設定する
        center: {lat: 35.675682101601765, lng: 139.66842469787593},
        zoom: 12,
    });
}

function openPopup() {
    const popup = document.getElementById('popup');
    popup.style.display = 'flex';
    displayMap('sp-map');
}

function closePopup() {
    const popup = document.getElementById('popup');
    popup.style.display = 'none';
}

function initMap() {
    displayMap('map');
}

window.onload = initMap;
