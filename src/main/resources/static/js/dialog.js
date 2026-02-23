document.addEventListener('DOMContentLoaded', function () {
    function closeDialog(modal, overlay) {
        if (modal) modal.style.display = 'none';
        if (overlay) overlay.style.display = 'none';
    }

    function openDialog(modal, overlay) {
        if (overlay) overlay.style.display = '';
        if (modal) modal.style.display = '';
    }

    // Hook cancel buttons
    document.querySelectorAll('.dialog-cancel').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const modal = e.target.closest('.dialog-modal');
            const overlay = document.querySelector('.dialog-overlay');
            closeDialog(modal, overlay);
        });
    });

    // Hook continue buttons
    document.querySelectorAll('.dialog-continue').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const continueUrl = btn.getAttribute('data-continue-url');
            if (continueUrl && continueUrl !== 'null') {
                window.location.href = continueUrl;
            } else {
                const modal = e.target.closest('.dialog-modal');
                const overlay = document.querySelector('.dialog-overlay');
                closeDialog(modal, overlay);
            }
        });
    });

    // Handle choice buttons
    document.querySelectorAll('.dialog-choice').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const choiceBtn = e.currentTarget;
            const continueUrl = choiceBtn.getAttribute('data-continue-url');
            const reply = choiceBtn.getAttribute('data-reply');
            const label = choiceBtn.textContent || choiceBtn.innerText;
            const modal = choiceBtn.closest('.dialog-modal');

            // hide choices immediately
            const choicesContainer = modal.querySelector('.choices');
            if (choicesContainer) choicesContainer.style.display = 'none';

            if (continueUrl && continueUrl !== 'null') {
                window.location.href = continueUrl;
                return;
            }

            // Append player's message
            const messagesRoot = modal.querySelector('.dialog-messages');
            if (messagesRoot) {
                const pMsg = document.createElement('div');
                pMsg.className = 'msg player';
                pMsg.innerHTML = '<div class="avatar">YOU</div><div class="bubble"></div>';
                pMsg.querySelector('.bubble').textContent = label;
                messagesRoot.appendChild(pMsg);
                messagesRoot.scrollTop = messagesRoot.scrollHeight;
            }

            // After a short delay, append NPC reply (or fallback to outroText)
            setTimeout(function () {
                const messagesRoot2 = modal.querySelector('.dialog-messages');
                let replyText = reply && reply !== 'null' ? reply : null;
                if (!replyText) {
                    const outroEl = modal.querySelector('.dialog-outro');
                    replyText = outroEl ? outroEl.textContent : '';
                }
                if (messagesRoot2) {
                    const nMsg = document.createElement('div');
                    nMsg.className = 'msg npc';
                    nMsg.innerHTML = '<div class="avatar">NPC</div><div class="bubble"></div>';
                    nMsg.querySelector('.bubble').textContent = replyText;
                    messagesRoot2.appendChild(nMsg);
                    messagesRoot2.scrollTop = messagesRoot2.scrollHeight;
                }
            }, 600);
        });
    });

    // Allow clicking overlay to close
    const overlay = document.querySelector('.dialog-overlay');
    if (overlay) overlay.addEventListener('click', function () {
        const modal = document.querySelector('.dialog-modal');
        closeDialog(modal, overlay);
    });
});
