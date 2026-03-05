const wrap = document.getElementById("body");
const textbox = document.getElementById("textbox");

const step = JSON.parse(wrap.dataset.step);
let id = Number.parseInt(wrap.dataset.id, 10);

let isTyping = false;
let textIsDisplayed = false;

render();

wrap.addEventListener("click", () => {
    render();
});

function render() {
    if(!textIsDisplayed){
        typeText(step.text ?? "End.");
    } else {
        window.location.href = "/rooms/" + (id + 1);
    }
}

function typeText(text, speed = 40) {
    console.log("True or False:" + isTyping);
    textbox.innerHTML = ""; // Clear previous content

    const spanArray = createSpanArray(text)

    if(isTyping){
        spanArray.forEach(span => {
            span.style.opacity = "1";
        });
        isTyping = false;
        textIsDisplayed = true;
    } else {
        isTyping = true;
        let i = 0;
        function type() {
            if (i < text.length) {
                spanArray[i].style.opacity = "1";
                i++;
                setTimeout(type, speed);
            } else {
                isTyping = false;
                textIsDisplayed = true;
            }
        }
        type();
    }
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