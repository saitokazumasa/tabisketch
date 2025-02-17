class ImageUrls {
    urls = [
        '/images/top1.png',
        '/images/top2.png',
        '/images/top3.png',
        '/images/top4.png',
        '/images/top5.png',
        '/images/top6.png',
    ];
    currentIndex = -1;

    nextUrl() {
        const nextIndex = this.currentIndex + 1;
        this.currentIndex = nextIndex >= this.urls.length? 0 : nextIndex;
        return this.urls[this.currentIndex];
    }
}

const imagesElement = document.getElementById('images');
const imageUrls = new ImageUrls();
function nextImage() {
    imagesElement.src = imageUrls.nextUrl();
}
setInterval(() => nextImage(), 10000);
