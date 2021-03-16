package pro.watchnews.watchnewspro.Viewmodal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContinentsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ContinentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Continents fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
