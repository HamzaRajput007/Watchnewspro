package pro.watchnews.watchnewspro.Viewmodal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReleventViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ReleventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
