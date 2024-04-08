package com.example.variant_9_task_2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputTemperatureEditText;
    private Spinner fromScaleSpinner;
    private Spinner toScaleSpinner;
    private EditText resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // инициализация пользовательского интерфейса
        inputTemperatureEditText = findViewById(R.id.inputTemperatureEditText); // вводимое число
        fromScaleSpinner = findViewById(R.id.fromScaleSpinner); // выбор из
        toScaleSpinner = findViewById(R.id.toScaleSpinner); // выбор в
        resultTextView = findViewById(R.id.resultTextView); // выводимое число

        // настройка Spiner
        setupSpinners();

        // Установка слушателя изменений в поле ввода температуры
        inputTemperatureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                convertTemperature();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setupSpinners() {
        String[] scales = getResources().getStringArray(R.array.temperature_scales); // массив названия шкал

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, scales); // адаптер для спинеров
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // вид элементов списка

        fromScaleSpinner.setAdapter(adapter);//
        toScaleSpinner.setAdapter(adapter); // отображение шкал темпиратур

        AdapterView.OnItemSelectedListener scaleItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        };

        fromScaleSpinner.setOnItemSelectedListener(scaleItemSelectedListener);
        toScaleSpinner.setOnItemSelectedListener(scaleItemSelectedListener);
    }

    private void convertTemperature() {
        String inputTemperatureStr = inputTemperatureEditText.getText().toString();
        double inputTemperature = parseTemperature(inputTemperatureStr);

        if (!Double.isNaN(inputTemperature)) {
            int fromScale = fromScaleSpinner.getSelectedItemPosition();
            int toScale = toScaleSpinner.getSelectedItemPosition();

            double convertedTemperature = convert(inputTemperature, fromScale, toScale);
            resultTextView.setText(String.valueOf(convertedTemperature));
        } else {
            resultTextView.setText("");
        }
    }

    // переобразуем число с плавующей точкой
    private double parseTemperature(String temperatureStr) {
        try {
            return Double.parseDouble(temperatureStr);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private double convert(double temperature, int fromScale, int toScale) {
        // Цельсия в Фаренгейт
        switch (fromScale) {
            case 0: // Цельсия
                switch (toScale) {
                    case 0: // Цельсия
                        return temperature;
                    case 1: // Фаренгейт
                        return temperature * 9 / 5 + 32;
                    case 2: // Кельвины
                        return temperature + 273.15;
                    case 3: // Реомюр
                        return temperature * ((double) 4 / 5);
                    case 4: // Ранкин
                        return (temperature + 273.15) * ((double) 9 /5);
                    default:
                        return temperature;
                }
            case 1: // Фаренгейт
                switch (toScale) {
                    case 0: // Цельсия
                        return (temperature - 32) * 5 / 9;
                    case 1: // Фаренгейт
                        return temperature;
                    // Добавьте другие случаи для других шкал температур по мере необходимости
                    case 2: // Кельвины
                        return (temperature +459.67) * ((double) 5 /9);
                    case 3: // Реомюр
                        return (temperature - 32) * ((double) 4 /9);
                    case 4: // Ранкина
                        return temperature + 459.67;
                    default:
                        return temperature;
                }
            case 2: // Кельвины
                switch (toScale) {
                    case 0: // Цельсия
                        return temperature + 273.15;
                    case 1: // Фаренгейт
                        return temperature * ((double) 9 /5) - 459.67;
                    case 2: // Кельвины
                        return temperature;
                    case 3: // Реомюр
                        return (temperature - 273.15) * 0.8;
                    case 4: // Ранкина
                        return temperature * ((double) 9 /5);
                    default:
                        return temperature;
                }
            case 3: // Реомюра
                switch (toScale) {
                    case 0: // Цельсия
                        return temperature * 5 / 4;
                    case 1: // Фаренгейт
                        return temperature * 9 / 4 + 32;
                    case 2: // Кельвин
                        return temperature * 5 / 4 + 273.15;
                    case 3: // Реомюра
                        return temperature;
                    case 4: // Ранкин
                        return (temperature * 5 / 4) + 273.15;
                    default:
                        return temperature;
                }
            case 4: // Ранкин
                switch (toScale) {
                    case 0: // Цельсия
                        return (temperature - 491.67) * 5 / 9;
                    case 1: // Фаренгейт
                        return temperature - 459.67;
                    case 2: // Кельвин
                        return temperature * 5 / 9;
                    case 3: // Реомюра
                        return (temperature - 491.67) * 4 / 5;
                    case 4: // Ранкин
                        return temperature;
                    default:
                        return temperature;
                }
            default:
                return temperature;
        }
    }
}