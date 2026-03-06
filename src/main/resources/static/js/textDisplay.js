const wrap = document.getElementById("body");
const textbox = document.getElementById("textbox");
const answerOptionsContainer = document.getElementById("answer-options");

let currentIndex = 0;
let currentQuestionIndex = 0;
let isTyping = false;
let textIsDisplayed = false;
let isAnsweringQuestion = false;

render();

wrap.addEventListener("click", () => {
    // Only allow clicks during dialogue phase, not during question answering
    if (!isAnsweringQuestion) {
        render();
    }
});

function render() {
    if (!textIsDisplayed) {
        // Dialogue phase
        if (currentIndex >= dialogue.length) {
            // Start questions phase
            startQuestionsPhase();
            return;
        }
        const entry = dialogue[currentIndex++];
        textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + entry.speaker.toLowerCase();
        typeText(entry.text);
    } else {
        textIsDisplayed = false;
        render();
    }
}

function startQuestionsPhase() {
    if (currentQuestionIndex >= questions.length) {
        // All questions completed
        showCompletionMessage();
        return;
    }
    displayQuestion(questions[currentQuestionIndex]);
}

function displayQuestion(question) {
    isAnsweringQuestion = true;
    // Show the question prompt as a TEACHER bubble with typewriter effect
    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--teacher";
    typeText(question.prompt, 40, () => {
        // After typewriter is done, show answer options
        showAnswerOptions(question);
    });
}

function typeText(text, speed = 40, onComplete = null) {
    console.log("True or False:" + isTyping);
    textbox.innerHTML = ""; // Clear previous content

    const spanArray = createSpanArray(text)

    if(isTyping){
        spanArray.forEach(span => {
            span.style.opacity = "1";
        });
        isTyping = false;
        textIsDisplayed = true;
        if (onComplete) onComplete();
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
                if (onComplete) onComplete();
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

function showAnswerOptions(question) {
    answerOptionsContainer.innerHTML = "";
    const optionsDiv = document.createElement("div");
    optionsDiv.className = "options-list";

    question.options.forEach((option, index) => {
        const button = document.createElement("button");
        button.className = "option-button";
        button.textContent = option.text;
        button.addEventListener("click", () => {
            handleAnswer(option, question);
        });
        optionsDiv.appendChild(button);
    });

    answerOptionsContainer.appendChild(optionsDiv);
}

function handleAnswer(selectedOption, question) {
    // Clear buttons immediately
    answerOptionsContainer.innerHTML = "";

    // Show feedback
    const feedbackText = selectedOption.correct
        ? "Richtig. +" + question.points + " Punkt"
        : "Leider falsch.";

    const feedbackClass = selectedOption.correct
        ? "bubble--teacher feedback-correct"
        : "bubble--teacher feedback-incorrect";

    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden " + feedbackClass;
    textbox.innerHTML = feedbackText;

    // After 1 second, move to next question
    setTimeout(() => {
        currentQuestionIndex++;
        isAnsweringQuestion = false;
        textIsDisplayed = false;
        startQuestionsPhase();
    }, 1000);
}

function showCompletionMessage() {
    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--system";
    textbox.innerHTML = "Tagestest abgeschlossen.";
    isAnsweringQuestion = false;
}
