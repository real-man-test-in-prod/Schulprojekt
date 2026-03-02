const gameElement = document.getElementById('game');
const aspectRatio = 1920/1080;
const aspectRatioInverse = Math.pow(aspectRatio, -1)


function handleResize() {
    console.log("If:" + window.innerWidth/window.innerHeight < aspectRatio);
    if(window.innerWidth/window.innerHeight < aspectRatio){
        gameElement.style.width = "100vw";
        gameElement.style.height = (((window.innerWidth/1920) * 1080) / window.innerHeight) * 100 + "vh";
    } else {
        gameElement.style.width = (((window.innerHeight/1080) * 1920) / window.innerWidth) * 100 + "vw";
        gameElement.style.height = "100vh";
    }
    console.log("Viewport changed!");
    // 1280 // 411
    console.log("New width:", window.innerWidth, "New height:", window.innerHeight);
}

window.addEventListener('resize', () => {
    window.requestAnimationFrame(handleResize);
});

handleResize();