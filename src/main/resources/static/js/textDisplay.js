const wrap = document.getElementById("body");
const textbox = document.getElementById("textbox");
const answerOptionsContainer = document.getElementById("answer-options");
const weiterBtn = document.getElementById("weiter-btn");
const zumTagestestBtn = document.getElementById("zum-tagestest-btn");

zumTagestestBtn.addEventListener("click", (event) => {
    event.stopPropagation();
    if (phase !== 'dialogue') return;
    clearTimeout(typewriterTimeout);
    typewriterTimeout = null;
    isTyping = false;
    textIsDisplayed = false;
    currentIndex = roomData.days[currentDayIndex].dialogue.length;
    render();
});

// Phases: 'dialogue' | 'test-intro' | 'questions' | 'test-outro' | 'day-transition' | 'complete'
const savedProgress = JSON.parse(localStorage.getItem('hammerProgress') || 'null');
const isSameRoom = savedProgress && savedProgress.roomId === roomData.roomCode;

let currentDayIndex = isSameRoom ? (savedProgress.currentDayIndex ?? 0) : 0;
let currentIndex = isSameRoom ? (savedProgress.currentDialogueSeq ?? 0) : 0;
let currentQuestionIndex = isSameRoom ? (savedProgress.currentQuestionIndex ?? 0) : 0;
let phase = isSameRoom ? (savedProgress.phase ?? 'dialogue') : 'dialogue';
let totalPoints = isSameRoom ? (savedProgress.totalPoints ?? 0) : 0;
let roomPoints = isSameRoom ? (savedProgress.roomPoints ?? 0) : 0;
let completedDays = isSameRoom ? (savedProgress.completedDays ?? []) : [];
let correctAnswers = isSameRoom ? (savedProgress.correctAnswers ?? 0) : 0;
let wrongAnswers = isSameRoom ? (savedProgress.wrongAnswers ?? 0) : 0;
let isTyping = false;
let textIsDisplayed = false;
let typewriterTimeout = null;

restorePhase();

// Button clicks use event.stopPropagation() so they never reach here.
// Remove any previously registered handler before adding, so the listener
// is never stacked if this script is evaluated more than once.
function handleWrapClick() {
    if (phase === 'questions') return;

    if (isTyping) {
        // Complete the current text immediately — do not advance.
        clearTimeout(typewriterTimeout);
        typewriterTimeout = null;
        textbox.querySelectorAll("span").forEach(span => span.style.opacity = "1");
        isTyping = false;
        textIsDisplayed = true;
        return;
    }

    if (textIsDisplayed) {
        textIsDisplayed = false;
        if (phase === 'dialogue') {
            render();
        } else if (phase === 'test-intro') {
            phase = 'questions';
            weiterBtn.style.display = 'none';
            currentQuestionIndex = 0;
            startQuestionsPhase();
        } else if (phase === 'test-outro') {
            advanceToNextDay();
        } else if (phase === 'day-transition') {
            phase = 'dialogue';
            zumTagestestBtn.style.display = '';
            currentIndex = 0;
            render();
        }
    }
}
wrap.removeEventListener("click", handleWrapClick);
wrap.addEventListener("click", handleWrapClick);

function render() {
    if (phase !== 'dialogue') return;

    const dayDialogue = roomData.days[currentDayIndex].dialogue;
    if (currentIndex >= dayDialogue.length) {
        phase = 'test-intro';
        zumTagestestBtn.style.display = 'none';
        const intro = roomData.days[currentDayIndex].dailyTest.intro;
        textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + intro.speaker.toLowerCase();
        typeText(intro.text);
        return;
    }

    const entry = dayDialogue[currentIndex++];
    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + entry.speaker.toLowerCase();
    typeText(entry.text);
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
    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--teacher";
    if (question.type === 'GAP') {
        showGapQuestion(question);
        return;
    }
    const promptText = question.prompt != null ? question.prompt : "";
    typeText(promptText, 40, () => {
        showAnswerOptions(question);
    });
}


function saveToLocalStorage() {
    localStorage.setItem("hammerProgress", JSON.stringify({
        roomId: roomData.roomCode,
        currentDayIndex,
        currentDialogueSeq: currentIndex,
        currentQuestionIndex,
        phase,
        totalPoints,
        roomPoints,
        completedDays,
        correctAnswers,
        wrongAnswers
    }));
}

function restorePhase() {
    switch (phase) {
        case 'dialogue':
            render();
            break;
        case 'test-intro': {
            zumTagestestBtn.style.display = 'none';
            const intro = roomData.days[currentDayIndex].dailyTest.intro;
            textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + intro.speaker.toLowerCase();
            typeText(intro.text);
            break;
        }
        case 'questions':
            weiterBtn.style.display = 'none';
            zumTagestestBtn.style.display = 'none';
            startQuestionsPhase();
            break;
        case 'test-outro': {
            weiterBtn.style.display = '';
            zumTagestestBtn.style.display = 'none';
            const outro = roomData.days[currentDayIndex].dailyTest.outro;
            textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + outro.speaker.toLowerCase();
            typeText(outro.text);
            break;
        }
        case 'day-transition': {
            zumTagestestBtn.style.display = 'none';
            const intro = roomData.days[currentDayIndex].dailyTest.intro;
            textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + intro.speaker.toLowerCase();
            typeText(intro.text);
            break;
        }
        case 'complete': {
            const msg = roomData.completionMessage;
            textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + msg.speaker.toLowerCase();
            textbox.innerHTML = msg.text;
            textIsDisplayed = true;
            break;
        }
        default:
            render();
    }
}

function showTestOutro() {
    if (!completedDays.includes(currentDayIndex)) {
        completedDays.push(currentDayIndex);
    }
    saveToLocalStorage();
    phase = 'test-outro';
    weiterBtn.style.display = '';
    const outro = roomData.days[currentDayIndex].dailyTest.outro;
    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + outro.speaker.toLowerCase();
    typeText(outro.text);
}

function advanceToNextDay() {
    const nextDayIndex = currentDayIndex + 1;
    if (nextDayIndex >= roomData.days.length) {
        phase = 'complete';
        const msg = roomData.completionMessage;
        textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + msg.speaker.toLowerCase();
        textbox.innerHTML = msg.text;
        textIsDisplayed = true;
        return;
    }
    currentDayIndex = nextDayIndex;
    phase = 'day-transition';
    const intro = roomData.days[currentDayIndex].dailyTest.intro;
    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--" + intro.speaker.toLowerCase();
    typeText(intro.text);
}

function typeText(text, speed = 40, onComplete = null) {
    console.log("isTyping:" + isTyping);
    if (typewriterTimeout) {
        clearTimeout(typewriterTimeout);
        typewriterTimeout = null;
    }
    textbox.innerHTML = "";

    const spanArray = createSpanArray(text);

    if (isTyping) {
        spanArray.forEach(span => span.style.opacity = "1");
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
                typewriterTimeout = setTimeout(type, speed);
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

    if (question.type === 'GAP') {
        showGapQuestion(question);
        return;
    }

    const optionsDiv = document.createElement("div");
    optionsDiv.className = "options-list";

    question.options.forEach(option => {
        const button = document.createElement("button");
        button.className = "option-button";
        button.textContent = option.text;
        button.addEventListener("click", (event) => {
            event.stopPropagation(); // Bug 1 fix: prevent click from reaching body listener
            handleAnswer(option, question);
        });
        optionsDiv.appendChild(button);
    });

    answerOptionsContainer.appendChild(optionsDiv);
}

function handleAnswer(selectedOption, question) {
    answerOptionsContainer.innerHTML = "";

    const feedbackText = selectedOption.correct
        ? "Richtig. +" + question.points + " Punkt"
        : "Leider falsch.";

    const feedbackClass = selectedOption.correct
        ? "bubble--teacher feedback-correct"
        : "bubble--teacher feedback-incorrect";

    textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden " + feedbackClass;
    textbox.innerHTML = feedbackText;

    if (selectedOption.correct) {
        totalPoints += question.points;
        roomPoints += question.points;
        correctAnswers++;
    } else {
        wrongAnswers++;
    }
    saveToLocalStorage();

    setTimeout(() => {
        currentQuestionIndex++;
        textIsDisplayed = false;
        startQuestionsPhase();
    }, 1000);
}

function showGapQuestion(question) {
    const collectedAnswers = {};
    let gapIndex = 0;

    function showNextGap() {
        if (gapIndex >= question.options.length) {
            submitGapAnswers(question, collectedAnswers);
            return;
        }

        const gap = question.options[gapIndex];
        const gapText = (gap.textBefore || "") + " ___ " + (gap.textAfter || "");

        answerOptionsContainer.innerHTML = "";
        textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden bubble--teacher";
        typeText(gapText, 40, () => {
            const optionsDiv = document.createElement("div");
            optionsDiv.className = "options-list";

            gap.choices.forEach(choice => {
                const button = document.createElement("button");
                button.className = "option-button";
                button.textContent = choice;
                button.addEventListener("click", (event) => {
                    event.stopPropagation();
                    collectedAnswers[gap.gapId] = choice;
                    gapIndex++;
                    answerOptionsContainer.innerHTML = "";
                    showNextGap();
                });
                optionsDiv.appendChild(button);
            });

            answerOptionsContainer.appendChild(optionsDiv);
        });
    }

    showNextGap();
}

function submitGapAnswers(question, answers) {
    fetch('/question/' + question.id, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ answers: answers })
    })
    .then(res => res.json())
    .then(points => {
        answerOptionsContainer.innerHTML = "";
        const isCorrect = points > 0;

        const feedbackText = isCorrect
            ? "Richtig. +" + question.points + " Punkt"
            : "Leider falsch.";

        const feedbackClass = isCorrect
            ? "bubble--teacher feedback-correct"
            : "bubble--teacher feedback-incorrect";

        textbox.className = "absolute p-[2%] text-2xl text-white font-bold text-outline-blue h-full overflow-hidden " + feedbackClass;
        textbox.innerHTML = feedbackText;

        if (isCorrect) {
            totalPoints += question.points;
            roomPoints += question.points;
            correctAnswers++;
        } else {
            wrongAnswers++;
        }
        saveToLocalStorage();

        setTimeout(() => {
            currentQuestionIndex++;
            textIsDisplayed = false;
            startQuestionsPhase();
        }, 1000);
    });
}
