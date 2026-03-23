// HTML Elements
const wrap = document.getElementById("body");
const textbox = document.getElementById("textbox");
const forwardBtn = document.getElementById("forward-btn");
const backwardBtn = document.getElementById("backward-btn");
const teacherImg = document.getElementById('teacher-img');
const studentImg = document.getElementById('student-img');

// Data from Backend
const roomDialogue = JSON.parse(wrap.getAttribute('data-roomDialogue'));

// Save state
const savedProgress = JSON.parse(localStorage.getItem('hammerProgress_' + roomDialogue.roomCode) || null);

// Variables for controlling the dialogue flow
let phase; // 'dialogue' | 'test-intro' | 'questions' | 'test-outro' | 'day-transition' | 'complete'
let dialogueIndex;
let dayIndex;
let currentSpeaker;

//Variables for managing the text displaying
let typeIndex;
let isTyping;

initializeData();
forward();

// ***
// User Input Management
// ***

forwardBtn.addEventListener('click', (event) => {
    forward();
});

textbox.addEventListener('mousedown', (event) => {
    if (event.button === 0) { // 0 is the code for left-click
        forward();
    }
});

wrap.addEventListener('keydown', (event) => {
    if (event.code === "Space") {
        event.preventDefault();
        forward();
    }
});

backwardBtn.addEventListener('click', (event) => {
    backward();
});

function initializeData() {
    if (savedProgress && savedProgress.roomId === roomDialogue.roomCode){
        phase = savedProgress.phase;
        dialogueIndex = savedProgress.dialogueIndex
        dayIndex = savedProgress.dayIndex
    } else {
        phase = 'dialogue';
        dialogueIndex = 0;
        dayIndex = 0;
    }
}

function forward() {
    switch (phase){
        case 'dialogue': {
            currentSpeaker = roomDialogue.days[dayIndex].dialogue[dialogueIndex].speaker;
            typeText(roomDialogue.days[dayIndex].dialogue[dialogueIndex].text, completeDialogue);
        }
    }
    highlightSpeaker();
}

function backward() {
    switch (phase){
        case 'dialogue': {
            if(dialogueIndex > 0){
                if(isTyping){
                    typeText(roomDialogue.days[dayIndex].dialogue[dialogueIndex].text, completeDialogue);
                }
                dialogueIndex = dialogueIndex - 2; // Minus 2 because completeDialogue automatically increases by 1
                isTyping = true // skips type animation
                currentSpeaker = roomDialogue.days[dayIndex].dialogue[dialogueIndex].speaker;
                typeText(roomDialogue.days[dayIndex].dialogue[dialogueIndex].text, completeDialogue);
            }
        }
    }
    highlightSpeaker();
}

// On text display completion in dialogue phase
function completeDialogue(){
    if(dialogueIndex + 1 === roomDialogue.days[dayIndex].dialogue.length){
        phase = 'test-intro';
    } else {
        dialogueIndex++;
    }
}

// Makes text visible at displayed speed and executes onComplete, when finished
function typeText(text, onComplete = null, speed = 40) {
    textbox.innerHTML = "";
    const spanArray = createSpanArray(text);

    if (isTyping) {
        typeIndex = ++text.length;
        spanArray.forEach(span => span.style.opacity = "1");
        isTyping = false;
        if (onComplete) onComplete();
    } else {
        isTyping = true;
        typeIndex = 0;

        function type() {
            if (typeIndex < text.length) {
                spanArray[typeIndex].style.opacity = "1";
                typeIndex++;
                typewriterTimeout = setTimeout(type, speed);
            } else if (typeIndex === text.length) {
                isTyping = false;
                textIsDisplayed = true;
                if (onComplete) onComplete();
            }
        }
        type();
    }
}

// Creates an Array where each letter is its own span,
// so the visibility can be controlled individually
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

function highlightSpeaker(){
    let isStudentSpeaking = currentSpeaker === 'STUDENT';
    if (isStudentSpeaking) {
        teacherImg.classList.add('opacity-50');
        teacherImg.classList.remove('opacity-100');
        studentImg.classList.add('opacity-100');
        studentImg.classList.remove('opacity-50');
    } else {
        teacherImg.classList.add('opacity-100');
        teacherImg.classList.remove( 'opacity-50');
        studentImg.classList.add('opacity-50');
        studentImg.classList.remove( 'opacity-100');
    }
}