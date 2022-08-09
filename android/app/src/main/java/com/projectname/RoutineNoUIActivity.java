package com.projectname;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.affirm.android.Affirm;
import com.affirm.android.model.Address;
import com.affirm.android.model.Billing;
import com.affirm.android.model.CardDetails;
import com.affirm.android.model.Checkout;
import com.affirm.android.model.Currency;
import com.affirm.android.model.Item;
import com.affirm.android.model.Name;
import com.affirm.android.model.Shipping;
import com.affirm.android.model.VcnReason;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RoutineNoUIActivity extends Activity implements Affirm.CheckoutCallbacks, Affirm.VcnCheckoutCallbacks, Affirm.PrequalCallbacks {

    private static final BigDecimal PRICE = BigDecimal.valueOf(1100.0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Affirm.startCheckout(RoutineNoUIActivity.this, checkoutModel(), true);
    }

    private Checkout checkoutModel() {
        final Item item = Item.builder()
                .setDisplayName("Great Deal Wheel")
                .setImageUrl(
                        "http://www.m2motorsportinc.com/media/catalog/product/cache/1/thumbnail" +
                                "/9df78eab33525d08d6e5fb8d27136e95/v/e/velocity-vw125-wheels-rims.jpg")
                .setQty(1)
                .setSku("wheel")
                .setUnitPrice(BigDecimal.valueOf(1000.0))
                .setUrl("http://merchant.com/great_deal_wheel")
                .build();

        final Map<String, Item> items = new HashMap<>();
        items.put("wheel", item);

        final Name name = Name.builder().setFull("John Smith").build();

        final Address address = Address.builder()
                .setCity("San Francisco")
                .setCountry("USA")
                .setLine1("333 Kansas st")
                .setState("CA")
                .setZipcode("94107")
                .build();

        final Shipping shipping = Shipping.builder().setAddress(address).setName(name).build();
        final Billing billing = Billing.builder().setAddress(address).setName(name).build();

        // More details on https://docs.affirm.com/affirm-developers/reference/the-metadata-object
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("webhook_session_id", "ABC123");
        metadata.put("shipping_type", "UPS Ground");
        metadata.put("entity_name", "internal-sub_brand-name");

        return Checkout.builder()
                .setOrderId("55555")
                .setItems(items)
                .setBilling(billing)
                .setShipping(shipping)
                .setShippingAmount(BigDecimal.valueOf(0.0))
                .setTaxAmount(BigDecimal.valueOf(100.0))
                .setTotal(PRICE)
                .setCurrency(Currency.USD) // For Canadian, you must set "CAD"; For American, this is optional, you can set "USD" or not set.
                .setMetadata(metadata)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Affirm.handleCheckoutData(this, requestCode, resultCode, data)) {
            return;
        }

        if (Affirm.handleVcnCheckoutData(this, requestCode, resultCode, data)) {
            return;
        }

        if (Affirm.handlePrequalData(this, requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAffirmCheckoutError(@Nullable String message) {
        Log.d("AffirmRNTest", "onAffirmCheckoutError message: " + message);
        finish();
    }

    @Override
    public void onAffirmCheckoutCancelled() {
        Log.d("AffirmRNTest", "onAffirmCheckoutCancelled");
        finish();
    }

    @Override
    public void onAffirmCheckoutSuccess(@NonNull String token) {
        Log.d("AffirmRNTest", "onAffirmCheckoutSuccess token: " + token);
        finish();
    }

    @Override
    public void onAffirmVcnCheckoutError(@Nullable String message) {
        Log.d("AffirmRNTest", "onAffirmVcnCheckoutError message: " + message);
        finish();
    }

    @Override
    public void onAffirmVcnCheckoutCancelled() {
        Log.d("AffirmRNTest", "onAffirmVcnCheckoutCancelled");
        finish();
    }

    @Override
    public void onAffirmVcnCheckoutCancelledReason(@NonNull VcnReason vcnReason) {
        Log.d("AffirmRNTest", "onAffirmVcnCheckoutCancelledReason vcnReason: " + vcnReason);
        finish();
    }

    @Override
    public void onAffirmVcnCheckoutSuccess(@NonNull CardDetails cardDetails) {
        Log.d("AffirmRNTest", "onAffirmVcnCheckoutSuccess: " + cardDetails);
        finish();
    }

    @Override
    public void onAffirmPrequalError(@Nullable String message) {
        finish();
    }
}
