package ixigo.example.apple.ixigohack.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.activity.BaseActivity;
import ixigo.example.apple.ixigohack.activity.PlacePickerActivity;
import ixigo.example.apple.ixigohack.adapters.PlannerFragmentListAdapter;
import ixigo.example.apple.ixigohack.eventBus.EventBusHelper;
import ixigo.example.apple.ixigohack.eventBus.PlacePickerEventBus;
import ixigo.example.apple.ixigohack.extras.AppConstants;
import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.DebugUtils;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerFragment extends BaseFragment implements PlannerFragmentListAdapter.PlannerFragmentInterface {

    int position;

    @BindView(R.id.planner_fragment_recycler)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    PlannerFragmentListAdapter adapter;

    String deviceId;

    @Override
    public void onDestroy() {
        EventBusHelper.unRegister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnPlacePicked obj) {
        if (obj != null && position == obj.getFragmentPosition()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(AppConstants.FIREBASE_CONSTANTS.FIREBASE_ROOT_NODE);
            DatabaseReference childNode = myRef.child(deviceId);

            FirebaseDataObject firebaseObject = new FirebaseDataObject(obj.getData(), position);

            DatabaseReference pushedChild = childNode.push();
            firebaseObject.setFirebaseKey(pushedChild.getKey());
            pushedChild.setValue(firebaseObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).makeToast("Complete");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).makeToast("Failure");
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).makeToast("Success");
                    }
                }
            });

            if (adapter != null) {
                adapter.addData(firebaseObject);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnUpdateEventCompleteEvent obj) {
        if (obj != null && position == obj.getPosition()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(AppConstants.FIREBASE_CONSTANTS.FIREBASE_ROOT_NODE);
            DatabaseReference childNode = myRef.child(deviceId);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(obj.getData().getFirebaseKey(), obj.getData());
            childNode.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    DebugUtils.log("complete");
                }
            });

            if (adapter != null) {
                adapter.updateData(obj.getData());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacePickerEventBus.OnUdpateEventStartDateEvent obj) {
        if (obj != null && position == obj.getPosition()) {
            DialogFragment newFragment = TimePickerPlannerFragment.newInstance(obj.getData(),
                    obj.getPosition(), getResources().getString(R.string.time_picker_title_select_end_time_update));
            newFragment.show(getChildFragmentManager(), "timePicker");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusHelper.register(this);
    }

    public static PlannerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION, position);

        PlannerFragment fragment = new PlannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_planner, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        deviceId = AndroidUtils.getDeviceId(getActivity());

        position = getArguments().getInt(AppConstants.FRAGMENT_EXTRAS.EXTRA_FRAGMENT_POSITION);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlannerFragmentListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onUpdateClick(FirebaseDataObject obj) {
        obj.setStartHour(null);

        DialogFragment newFragment = TimePickerPlannerFragment.newInstance(obj, position, getResources().getString(R.string.time_picker_title_select_start_time_update));
        newFragment.show(getChildFragmentManager(), "timePicker");
    }
}
