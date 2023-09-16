package kg.hackathon.template.ui.auth.data

import kg.hackathon.template.base.Action


sealed class LoginAction: Action() {
    class OnUsernameChange(val data: String?): LoginAction()
    class OnPasswordChange(val data: String?): LoginAction()
    object OnRegisterClick: LoginAction()
    object OnLoginClick: LoginAction()
}