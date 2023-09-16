package kg.hackathon.template.ui.register_type

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.databinding.FragmentRegisterTypeBinding
import kg.hackathon.template.ui.register.data.RegisterScreenState
import kg.hackathon.template.ui.register_type.data.RegisterTypeAction
import kg.hackathon.template.ui.register_type.data.RegisterTypeSideEffect
import kg.hackathon.template.utils.navigateTo

@AndroidEntryPoint
class RegisterTypeFragment : BaseMviFragment<RegisterScreenState, FragmentRegisterTypeBinding, RegisterTypeVM>(
    FragmentRegisterTypeBinding::inflate
) {
    override val vm: RegisterTypeVM by viewModels()

    override fun initViews() = with(vb) {
        super.initViews()
        btnCustomer.setOnClickListener {
            vm.doAction(RegisterTypeAction.OnCustomerClick)
        }
        btnSeller.setOnClickListener {
            vm.doAction(RegisterTypeAction.OnSellerClick)
        }
    }

    override suspend fun onRender(state: RegisterScreenState) {
        super.onRender(state)
    }

    override suspend fun onSideEffect(sideEffect: SideEffect) {
        super.onSideEffect(sideEffect)
        when (sideEffect) {
            is RegisterTypeSideEffect.OpenRegisterScreen ->
                navigateTo(RegisterTypeFragmentDirections.toRegister(sideEffect.type))
        }
    }
}