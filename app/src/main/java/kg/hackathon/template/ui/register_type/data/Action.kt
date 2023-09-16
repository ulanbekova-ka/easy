package kg.hackathon.template.ui.register_type.data

import kg.hackathon.template.base.Action

sealed class RegisterTypeAction: Action() {
    object OnCustomerClick: RegisterTypeAction()
    object OnSellerClick: RegisterTypeAction()
}
