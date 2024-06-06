package com.example.toastproblem.ui.theme;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.evotor.framework.core.IntegrationService;
import ru.evotor.framework.core.action.event.receipt.changes.position.IPositionChange;
import ru.evotor.framework.core.action.event.receipt.changes.receipt.SetPurchaserContactData;
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEvent;
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEventProcessor;
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEventResult;
import ru.evotor.framework.core.action.processor.ActionProcessor;

public class MyDiscount extends IntegrationService {

    @Nullable
    @Override
    protected Map<String, ActionProcessor> createProcessors() {

        Map<String, ActionProcessor> map = new HashMap<>();

        map.put(ReceiptDiscountEvent.NAME_SELL_RECEIPT, new ReceiptDiscountEventProcessor() {

            @Override
            public void call(@NonNull String action, @NonNull ReceiptDiscountEvent event, @NonNull Callback callback) {

                BigDecimal discount = new BigDecimal(100).setScale(1);
                List<IPositionChange> changes = new ArrayList<>();

//              Не работает и без этого.
//              Handler handler = new Handler(Looper.getMainLooper());
//              handler.post(() -> Toast.makeText(getApplicationContext(), "Привет!", Toast.LENGTH_SHORT).show());

                try {
                    Log.d("MyDiscount", "call: " + event.getDiscount());
                    callback.onResult(
                            new ReceiptDiscountEventResult(
                                    discount,
                                    null,
                                    changes,
                                    null, null)
                    );
                } catch (RemoteException exc) {
                    exc.printStackTrace();
                }
            }
        });
        return map;
    }
}