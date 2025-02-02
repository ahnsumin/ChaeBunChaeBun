package com.E2I3.chaebunchaebun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageCommunityCommentDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageCommunityCommentDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager vp;
    TabLayout tabLayout;
    ImageView myCommentBack;
    String userId;

    public MypageCommunityCommentDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageMyCommentDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageCommunityCommentDetailFragment newInstance(String param1, String param2) {
        MypageCommunityCommentDetailFragment fragment = new MypageCommunityCommentDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View myCommentDetail = inflater.inflate(R.layout.fragment_mypage_community_mycomment_detail, container, false);

        vp = (ViewPager) myCommentDetail.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) myCommentDetail.findViewById(R.id.tab_layout);
        myCommentBack = (ImageView) myCommentDetail.findViewById(R.id.id_back);

        userId = getArguments().getString("userId");

        MyCommentVPAdapter adapter = new MyCommentVPAdapter(getChildFragmentManager(), userId);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        myCommentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        return myCommentDetail;
    }
}