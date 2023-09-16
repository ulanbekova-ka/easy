package kg.hackathon.template.ui.account

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.databinding.FragmentAccountBinding
import kg.hackathon.template.ui.account.data.AccountState
import kg.hackathon.template.utils.convertBase64ToBitmap

@AndroidEntryPoint
class AccountFragment : BaseMviFragment<AccountState, FragmentAccountBinding, AccountVM>
    (FragmentAccountBinding::inflate) {

    override val vm: AccountVM by viewModels()

    override fun initViews() = with(vb) {}

    override suspend fun onRender(state: AccountState) {
        state.user?.let {
            with(vb) {
                it.profileImage?.let { avatar.setImageBitmap(it.convertBase64ToBitmap()) }
                name.text = it.fullName
                login.text = it.login
            }
        }
    }

    override suspend fun onSideEffect(sideEffect: SideEffect) {
        super.onSideEffect(sideEffect)
    }
}