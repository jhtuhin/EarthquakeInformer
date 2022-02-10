package com.example.earthquakeinformer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.earthquakeinformer.adapters.EarthquakeAdapter;
import com.example.earthquakeinformer.databinding.FragmentDataBinding;
import com.example.earthquakeinformer.pickers.DatePickerDialogFragment;
import com.example.earthquakeinformer.utils.Constants;
import com.example.earthquakeinformer.viewmodels.InformerViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataFragment extends Fragment {
    private FragmentDataBinding binding;
    private InformerViewModel viewModel;
    private Integer[] magnitudeForSp= {1,2,3,4,5,6,7,8,9,10};
    int magnitude;
    String dateString;
    int year;
    int month;
    int day;

    public DataFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDataBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(this).get(InformerViewModel.class);
        final EarthquakeAdapter adapter = new EarthquakeAdapter();
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        binding.informationRV.setLayoutManager(llm);
        binding.informationRV.setAdapter(adapter);


        initDateAndTime();
       final ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_dropdown_item_1line,magnitudeForSp);
        binding.magnitudeSp.setAdapter(arrayAdapter);
        binding.magnitudeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                magnitude=Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.fromDateBtn.setOnClickListener(v -> new DatePickerDialogFragment().getChildFragmentManager()
                .setFragmentResultListener(Constants.REQUEST_KEY, getViewLifecycleOwner(), (requestKey, result) -> {
                    if(result.containsKey(Constants.DATE_KEY)){
                        dateString=result.getString(Constants.DATE_KEY);
                        day=result.getInt(Constants.DAY);
                        month=result.getInt(Constants.MONTH);
                        year=result.getInt(Constants.YEAR);
                        binding.fromDateBtn.setText(dateString);
                    }
                }));
        binding.toDateBtn.setOnClickListener(view -> {
            new DatePickerDialogFragment(). getChildFragmentManager().setFragmentResultListener(Constants.REQUEST_KEY, this, (requestKey, result) -> {
                if(result.containsKey(Constants.DATE_KEY)){
                    dateString=result.getString(Constants.DATE_KEY);
                    day=result.getInt(Constants.DAY);
                    month=result.getInt(Constants.MONTH);
                    year=result.getInt(Constants.YEAR);
                    binding.toDateBtn.setText(dateString);
                }
                else if(result.containsKey(Constants.DATE_KEY)){
                    //will use if necessary
                }
            });
        });

        viewModel.setEndDate(binding.toDateBtn.getText().toString());
        viewModel.setStartDate(binding.fromDateBtn.getText().toString());
        binding.goBtn.setOnClickListener(view -> {
            viewModel.getEarthQuakeLiveData().observe(getViewLifecycleOwner(),
                    earthQuakeModel -> {
                        viewModel.loadData();
                        adapter.submitList(earthQuakeModel.getList());
                    });
        });

        return binding.getRoot();

}

    private void initDateAndTime() {
        final Calendar calendar=Calendar.getInstance(Locale.getDefault());
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        dateString=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }
    }