const wrap = document.getElementById("body");
const textbox = document.getElementById("textbox");
const answerOptionsContainer = document.getElementById("answer-options");

// Phases: 'dialogue' | 'test-intro' | 'questions' | 'test-outro' | 'complete'
let phase = 'dialogue';
let currentDayIndex = 0;
let currentIndex = 0;
let currentQuestionIndex = 0;
let isTyping = false;
let isDisplayingText = false;
let isDisplayingQuestion = false;
let currentText = "";
let displayIndex;

render();

wrap.addEventListener("click", () => {
    moveForward()
});

function moveForward() {
    if(phase === 'questions' && !isDisplayingQuestion){
        console.log("DOING THIS")
        showAnswerOptions(allDaysQuestions[currentDayIndex][currentQuestionIndex])
    }
    if (isTyping) {
        finishTextDisplay();
        return;
    }
    if (isDisplayingText) {
        isDisplayingText = false;
        if (phase === 'dialogue') {
            render();
        } else if (phase === 'test-intro') {
            phase = 'questions';
            currentQuestionIndex = 0;
            startQuestionsPhase();
        } else if (phase === 'test-outro') {
            advanceToNextDay();
        } else if (phase === 'day-transition') {
            phase = 'dialogue';
            currentIndex = 0;
            render();
        }
    } else {
        render();
    }
}

function render() {
    if (phase !== 'dialogue') return;

    const dayDialogue = roomData.days[currentDayIndex].dialogue;
    if (currentIndex >= dayDialogue.length) {
        phase = 'test-intro';
        const intro = roomData.days[currentDayIndex].dailyTest.intro;
        setText(intro.text);
        return;
    }

    const entry = dayDialogue[currentIndex++];
    setText(entry.text);
}

function startQuestionsPhase() {
    const questions = allDaysQuestions[currentDayIndex];
    if (currentQuestionIndex >= questions.length) {
        showTestOutro();
        return;
    }
    displayQuestion(questions[currentQuestionIndex]);
}

function displayQuestion(question) {
    setText(question.prompt, 40, () => {
        showAnswerOptions(question);
    });
}

function showTestOutro() {
    phase = 'test-outro';
    const outro = roomData.days[currentDayIndex].dailyTest.outro;
    setText(outro.text);
}

function advanceToNextDay() {
    const nextDayIndex = currentDayIndex + 1;
    if (nextDayIndex >= roomData.days.length) {
        phase = 'complete';
        const msg = roomData.completionMessage;
        textbox.innerHTML = msg.text;
        isDisplayingText = true;
        return;
    }
    currentDayIndex = nextDayIndex;
    phase = 'day-transition';
    const intro = roomData.days[currentDayIndex].dailyTest.intro;
    setText(intro.text);
}

function finishTextDisplay() {
    textbox.innerHTML = "";

    const spanArray = createSpanArray(currentText);
    spanArray.forEach(span => span.style.opacity = "1");
    displayIndex = -1
    isTyping = false;
    isDisplayingText = true;
}

function setText(text, speed = 40, onComplete = null) {
    currentText = text;
    textbox.innerHTML = "";

    const spanArray = createSpanArray(currentText);
    isTyping = true;
    displayIndex = 0;

    function type() {
        if (displayIndex < currentText.length && displayIndex !== -1) {
            spanArray[displayIndex].style.opacity = "1";
            displayIndex++;
            setTimeout(type, speed);
        } else {
            isTyping = false;
            isDisplayingText = true;
            if(onComplete) onComplete();
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

function showAnswerOptions(question) {
    answerOptionsContainer.innerHTML = "";
    const optionsDiv = document.createElement("div");
    optionsDiv.className = "options-list";

    question.options.forEach(option => {
        const button = document.createElement("button");
        button.className = "option-button";
        button.textContent = option.text;
        button.addEventListener("click", (event) => {
            event.stopPropagation();
            handleAnswer(option, question);
        });
        optionsDiv.appendChild(button);
    });

    answerOptionsContainer.appendChild(optionsDiv);
    isDisplayingQuestion = true;
}

function handleAnswer(selectedOption, question) {
    answerOptionsContainer.innerHTML = "";

    textbox.innerHTML = selectedOption.correct
        ? "Richtig. +" + question.points + " Punkt"
        : "Leider falsch.";

    setTimeout(() => {
        isDisplayingQuestion = false;
        currentQuestionIndex++;
        isDisplayingText = false;
        startQuestionsPhase();
    }, 1000);
}
