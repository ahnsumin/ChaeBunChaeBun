package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryCarrotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCarrotFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView categoryCarrotList;
    private ArrayList<CategoryListItem> categoryListItems;
    private CategoryListAdapter categoryListAdapter;
    private LinearLayoutManager cLayoutManager;

    TextView categoryNoList;
    String id = "1";
    String category = "4";
    ArrayList<String> postId;

    public CategoryCarrotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryCarrotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryCarrotFragment newInstance(String param1, String param2) {
        CategoryCarrotFragment fragment = new CategoryCarrotFragment();
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

        categoryListItems = new ArrayList<CategoryListItem>();
        categoryListItems.clear();
        postId = new ArrayList<String>();
        postId.clear();

        String resultText = "[NULL]";

        try {
            resultText = new HomeTask("posts/category/" + category + "/" + id).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(resultText);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);

                String post = subJsonObject.getString("posts");
                JSONArray jsonPostArray = new JSONArray(post);
                for(int j = 0; j < jsonPostArray.length(); j++){
                    JSONObject subJsonObject2 = jsonPostArray.getJSONObject(j);
                    String profile = subJsonObject2.getString("profile");
                    String title = subJsonObject2.getString("title");
                    String nickname = subJsonObject2.getString("nickname");
                    String writtenBy = subJsonObject2.getString("witten_by");
                    String content = subJsonObject2.getString("contents");

                    String img1 = null;
                    String img2 = null;
                    String img3 = null;
                    String img4 = null;
                    String img5 = null;

                    String imgs = subJsonObject2.getString("imgs");
                    JSONObject subJsonObject3 = new JSONObject(imgs);
                    img1 = subJsonObject3.getString("img1");
                    img2 = subJsonObject3.getString("img2");
                    img3 = subJsonObject3.getString("img3");
                    img4 = subJsonObject3.getString("img4");
                    img5 = subJsonObject3.getString("img5");

                    String buyDate = subJsonObject2.getString("buy_date");
                    String member = subJsonObject2.getString("headcounts") + "명";
                    String perPrice = subJsonObject2.getString("per_price");
                    int myWishInt = subJsonObject2.getInt("wish_cnts");
                    String myWish = String.valueOf(myWishInt);
                    int commentInt = subJsonObject2.getInt("comment_cnts");
                    String comment = String.valueOf(commentInt);
                    int isAuth = subJsonObject2.getInt("isAuth");

                    this.postId.add(subJsonObject2.getString("post_id"));

                    categoryListItems.add(new CategoryListItem(profile, title, nickname, writtenBy, content, img1, img2, img3, img4, img5, buyDate, member, perPrice, myWish, comment, isAuth));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ImageButton writing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View categoryCarrot = inflater.inflate(R.layout.fragment_category_carrot, container, false);

        categoryCarrotList = categoryCarrot.findViewById(R.id.carrot_list);
        categoryNoList = categoryCarrot.findViewById(R.id.carrot_nolist);

        if(categoryListItems.isEmpty()) {
            categoryNoList.setVisibility(View.VISIBLE);
        } else {
            categoryNoList.setVisibility(View.GONE);
            cLayoutManager = new LinearLayoutManager(getContext());
            categoryCarrotList.setLayoutManager(cLayoutManager);
            categoryListAdapter = new CategoryListAdapter(categoryListItems);
            categoryCarrotList.setAdapter(categoryListAdapter);

            categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    Bundle articleBundle = new Bundle();
                    articleBundle.putString("postId", postId.get(pos));
                    FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ArticleFragment articleFragment = new ArticleFragment();
                    articleFragment.setArguments(articleBundle);
                    articleTransaction.replace(R.id.bottom_frame, articleFragment);
                    articleTransaction.addToBackStack(null);
                    articleTransaction.commit();
                }
            });

            categoryListAdapter.setModalClickListener(new CategoryListAdapter.OnModalClickListener() {
                @Override
                public void OnModlaClick() {
                    if (categoryListAdapter.getItemCount() % 2 != 0) {
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                    } else {
                        MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                        myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                    }
                }
            });
        }

        writing = (ImageButton) categoryCarrot.findViewById(R.id.carrot_newbtn);
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), WarningDialogFragment.class));
            }
        });
        // Inflate the layout for this fragment
        return categoryCarrot;
    }
}