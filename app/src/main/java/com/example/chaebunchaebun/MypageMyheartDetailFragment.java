package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageMyheartDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageMyheartDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager vp;
    TabLayout tabLayout;
    Button btn;
    ImageView back;
    String userId;

    public MypageMyheartDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageMyheartDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageMyheartDetailFragment newInstance(String param1, String param2) {
        MypageMyheartDetailFragment fragment = new MypageMyheartDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId){
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myHeartDetail = inflater.inflate(R.layout.fragment_mypage_myheart_detail, container, false);

        vp = (ViewPager) myHeartDetail.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) myHeartDetail.findViewById(R.id.tab_layout);

        userId = getArguments().getString("userId");

        MyHeartVPAdapter adapter = new MyHeartVPAdapter(getChildFragmentManager(), userId);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return myHeartDetail;
    }
}