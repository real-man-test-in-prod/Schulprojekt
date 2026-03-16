const gameElement = document.getElementById('game');
const aspectRatio = 1920/1080;


function handleResize() {
    if(window.innerWidth/window.innerHeight < aspectRatio){
        gameElement.style.width = "100vw";
        gameElement.style.height = (((window.innerWidth/1920) * 1080) / window.innerHeight) * 100 + "vh";
    } else {
        gameElement.style.width = (((window.innerHeight/1080) * 1920) / window.innerWidth) * 100 + "vw";
        gameElement.style.height = "100vh";
    }
}

window.addEventListener('resize', () => {
    window.requestAnimationFrame(handleResize);
});

handleResize();