const wrap = document.getElementById("dialogue-wrap");
const textbox = document.getElementById("textbox");

const raw = wrap.dataset.dialogue;
const dialogue = JSON.parse(raw);

let i = 0;

function render() {
    const line = dialogue[i]?.line;
    textbox.textContent = line?.text ?? "End.";
}

render();

wrap.addEventListener("click", () => {
    if (i < dialogue.length - 1) i++;
    render();
});