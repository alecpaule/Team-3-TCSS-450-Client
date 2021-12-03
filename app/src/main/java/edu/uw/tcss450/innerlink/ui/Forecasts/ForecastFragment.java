package edu.uw.tcss450.innerlink.ui.Forecasts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentForecastBinding;


/**
 * Represents the Forecast screen.
 */
public class ForecastFragment extends Fragment {
    private ForecastCurrentViewModel mForecastCurrentModel;
    private ForecastHourlyViewModel mForecastHourlyModel;
    private ForecastDailyViewModel mForecastDailyModel;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mForecastCurrentModel = provider.get(ForecastCurrentViewModel.class);
        mForecastHourlyModel = provider.get(ForecastHourlyViewModel.class);
        mForecastDailyModel = provider.get(ForecastDailyViewModel.class);

        mForecastCurrentModel.getCurrConditions(98335);
        mForecastHourlyModel.getHourlyConditions(98335);
        mForecastDailyModel.getDailyConditions(98335);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentForecastBinding binding = FragmentForecastBinding.bind(getView());

        // current forecast
        mForecastCurrentModel.addForecastObserver(getViewLifecycleOwner(), forecast -> {
            binding.textCity.setText(forecast.getCity());
            binding.textTemperature.setText(forecast.getTemperature());
            binding.textCondition.setText(forecast.getCondition());

            String condition = forecast.getCondition();
            if (condition.equalsIgnoreCase("Clouds")) {
                binding.imageCondition.setImageResource(R.drawable.ic_cloud_24dp);
            } else if (condition.equalsIgnoreCase("Rain")) {
                binding.imageCondition.setImageResource(R.drawable.ic_rain_24dp);
            } else if (condition.equalsIgnoreCase("Snow")) {
                binding.imageCondition.setImageResource(R.drawable.ic_snow_24dp);
            } else {
                binding.imageCondition.setImageResource(R.drawable.ic_sunny_24dp);
            }
        });

        // hourly forecast
        mForecastHourlyModel.addForecastObserver(getViewLifecycleOwner(), forecastList -> {
            binding.listForecastHourly.setAdapter(new ForecastHourlyRecyclerViewAdapter(forecastList));
        });

        // daily forecast
        mForecastDailyModel.addForecastObserver(getViewLifecycleOwner(), forecastList -> {
            binding.listForecastDaily.setAdapter(new ForecastDailyRecyclerViewAdapter(forecastList));
        });
    }
}