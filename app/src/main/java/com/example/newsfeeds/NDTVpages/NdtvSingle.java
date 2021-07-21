package com.example.newsfeeds.NDTVpages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsfeeds.Adapter.NdtvAdapter;
import com.example.newsfeeds.Model.NdtvModel;
import com.example.newsfeeds.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class NdtvSingle extends Fragment {

    String title;
    NdtvAdapter ndtvAdapter;
    RecyclerView recyclerView;
    View view;
    List<NdtvModel> data;
    SwipeRefreshLayout refreshLayout;

    public static Fragment getInstance(String s) {
        NdtvSingle ndtvSingle = new NdtvSingle();

        ndtvSingle.title = s;
        return ndtvSingle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ndtv_single, container, false);
        refreshLayout = view.findViewById(R.id.swiperefresh);

        loadList();
//----------------------------------Refresh layout----------------------------------------------
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.green),
                        getResources().getColor(R.color.orange));
                refreshLayout.setRefreshing(true);
                loadList();
                refreshLayout.setRefreshing(false);
            }
        });
//----------------------------------Exit dialog start----------------------------------------------

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("Do you want to Exit ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    getActivity().finish();
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

//----------------------------------Exit dialog end----------------------------------------------
        return view;

    }

    private void loadList() {

        if (title.equals("Top Stories")) {
            getdata(getString(R.string.india));

        } else if (title.equals("Latest stories")) {
            getdata(getString(R.string.latest));

        } else if (title.equals("Trending Stories")) {
            getdata(getString(R.string.trending));

        } else if (title.equals("India")) {
            getdata(getString(R.string.india));

        } else if (title.equals("World")) {
            getdata(getString(R.string.world));

        } else if (title.equals("Business")) {
            getdata(getString(R.string.business));

        } else if (title.equals("Movies")) {
            getdata(getString(R.string.movies));

        } else if (title.equals("Sports")) {
            getdata(getString(R.string.sports));

        } else if (title.equals("Cricket")) {
            getdata(getString(R.string.cricket));

        } else if (title.equals("Tech")) {
            getdata(getString(R.string.tech));

//        }else if (title.equals("Auto")) {
//            getdata(getString(R.string.auto));

        } else if (title.equals("Health")) {
            getdata(getString(R.string.health));

        } else if (title.equals("South")) {
            getdata(getString(R.string.south));

        } else if (title.equals("People")) {
            getdata(getString(R.string.people));

        }
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();

    }

    private void getdata(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(String.valueOf(response), "abc");
                data = new ArrayList<NdtvModel>();
                try {

                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(new InputSource(new StringReader(response)));

                    Element element = doc.getDocumentElement();
                    element.normalize();

                    NodeList nList = doc.getElementsByTagName("item");
//                    Log.i(String.valueOf(doc), "nl123");

                    for (int i = 0; i < nList.getLength(); i++) {
                        Node node = nList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element2 = (Element) node;

                            NdtvModel modelRecycler = new NdtvModel();

                            modelRecycler.title = getValue("title", element2);
                            modelRecycler.link = getValue("link", element2);
                            modelRecycler.updatedAt = getValue("updatedAt", element2);
                            modelRecycler.pubDate = getValue("pubDate", element2);
                            modelRecycler.StoryImage = getValue("StoryImage", element2);
                            modelRecycler.category = getValue("category", element2);
                            modelRecycler.description = getValue("description", element2);
                            modelRecycler.fullimage = getValue("fullimage", element2);
                            modelRecycler.source = getValue("source", element2);

                            data.add(modelRecycler);

                            recyclerView = view.findViewById(R.id.recyclerView);
                            ndtvAdapter = new NdtvAdapter(getActivity(), data);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(ndtvAdapter);
                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                        }
                    }
                    view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }

            }
        }, null);

        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
