const teacherImg = document.getElementById('teacher-img');
const studentImg = document.getElementById('student-img');
const character = JSON.parse(wrap.dataset.step).character;

let isInactive = character === 'SCHUELER';

console.log(step)

if (isInactive) {
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