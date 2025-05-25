document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.querySelector('section');

    const signupButton = document.querySelector('button');
    signupButton.addEventListener('click', function () {
        const emailInput = document.querySelector('input[type="email"]');
        const passwordInput = document.querySelector('input[type="password"]');
        const confirmPasswordInput = document.querySelector('input[type="password"][name="passwordcon"]');

        const isValid = emailInput.checkValidity() && passwordInput.checkValidity() && confirmPasswordInput.checkValidity();


    });
});
