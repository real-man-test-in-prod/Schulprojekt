const wrap = document.getElementById("body");
const textbox = document.getElementById("textbox");

const dialogue = JSON.parse(wrap.dataset.dialogue);
let id = Number.parseInt(wrap.dataset.id, 10);

let isTyping = false;

render();

wrap.addEventListener("click", () => {
    render();
});

function render() {
    typeText(dialogue.text ?? "End.");
}

function typeText(text, speed = 40) {
    textbox.innerHTML = ""; // Clear previous content

    const spanArray = createSpanArray(text)

    let i = 0;
    function type() {
        if (i < text.length) {
            spanArray[i].style.opacity = "1";
            i++;
            setTimeout(type, speed);
        }
    }
    type();
}

function createSpanArray(text) {
    const allSpans = [];

    const words = text.split(" ");
    words.forEach((word, wordIndex) => {
        const wordWrapper = document.createElement("span");
        wordWrapper.style.display = "inline-block";
        wordWrapper.style.whiteSpace = "nowrap";

        word.split("").forEach(char => {
            const charSpan = document.createElement("span");
            charSpan.textContent = char;
            charSpan.style.opacity = "0";

            wordWrapper.appendChild(charSpan);
            allSpans.push(charSpan);
        });

        textbox.appendChild(wordWrapper);

        if (wordIndex < words.length - 1) {
            const space = document.createElement("span");
            space.textContent = "\u00A0";
            space.style.opacity = "0";

            textbox.appendChild(space);
            allSpans.push(space);
        }
    });

    return allSpans;
}