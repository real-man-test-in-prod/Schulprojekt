const wrap = document.getElementById("dialogue-wrap");
const textbox = document.getElementById("textbox");

const dialogue = JSON.parse(wrap.dataset.dialogue);
let id = Number.parseInt(wrap.dataset.id, 10);

function render() {
    console.log(id)
    const line = dialogue[id]?.line;
    typeText(line?.text ?? "End.");
}

function typeText(text, speed = 40) {
    textbox.textContent = "";
    let i = 0;
    function type() {
        if (i < text.length) {
            textbox.textContent += text.charAt(i);
            i++;
            setTimeout(type, speed);
        }
    }
    type();
}

render();

wrap.addEventListener("click", () => {
    if (id < dialogue.length - 1) id++;
    render();
});