package kg.hackathon.template.ui.register.data

import kg.hackathon.template.base.Action

sealed class RegisterAction: Action() {
    class OnUsernameChange(val data: String?): RegisterAction()
    class OnPasswordChange(val data: String?): RegisterAction()
    class OnRepPasswordChange(val data: String?): RegisterAction()
    class OnNameChange(val data: String?): RegisterAction()
    class OnBirthdayChange(val data: String?): RegisterAction()
    class OnEmailChange(val data: String?): RegisterAction()
    class OnAddressChange(val data: String?): RegisterAction()
    class OnInstagramChange(val data: String?): RegisterAction()
    class OnPhoneChange(val data: String?): RegisterAction()
    class OnPhotoDone(val photo: ByteArray): RegisterAction()
    class OnRegisterClick(val type: UserType): RegisterAction()
}