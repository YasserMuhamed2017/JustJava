package com.example.android.justjava; /**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Editable;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
    public class MainActivity extends AppCompatActivity {
    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {

        quantity++;
        if (quantity >= 100) {
            quantity = 100;
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_LONG).show();
            displayQuantity(quantity);

        }
        else
            displayQuantity(quantity);
    }
    public void decrement(View view){

        quantity--;
        /********if (quantity == 1) {
            // Show an error message as a toast

            // Exit this method early because there's nothing left to do
            return;
        }**********/
        if (quantity < 1) {
            quantity = 1;
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            displayQuantity(quantity);

        }
        else
            displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submit(View view) {
        // Figure out if the user wants whipped cream topping
         CheckBox whippedCreamCheckedBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
         boolean hasWhippedCream = whippedCreamCheckedBox.isChecked();

         // Figure out if the user wants whipped cream topping
         CheckBox chocolateCheckedBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
         boolean hasChocolate = chocolateCheckedBox.isChecked();
         // figure out if the user wants inputType
         EditText inputType = (EditText) findViewById(R.id.name_view);
         Editable hasInputType = inputType.getText();


         displayMessage(createOrderSummary( hasInputType,hasChocolate,hasWhippedCream ));



      Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java app for " + hasInputType);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(hasInputType , hasChocolate , hasWhippedCream));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


     public String createOrderSummary( Editable addInputType , boolean addChocolate , boolean addWhippedCream ){
        String priceMessage= getString(R.string.order_summary_name,addInputType); //"Name : " + addInputType ;
        priceMessage +="\n"+getString(R.string.order_summary_whipped_cream , addWhippedCream);
        priceMessage +="\n"+getString(R.string.order_summary_chocolate , addChocolate);
        priceMessage += "\n"+getString(R.string.order_summary_quantity , quantity);
        priceMessage +="\n"+getString(R.string.order_summary_price , NumberFormat.getCurrencyInstance().format
                (calculatePrice(addChocolate,addWhippedCream)));
        priceMessage += "\n"+getString(R.string.thank_you);

        return priceMessage;
    }
    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean addChocolate , boolean addWhippedCream) {
        int basePrice=5;

        if (addChocolate)
            basePrice += 2;
        if (addWhippedCream)
            basePrice += 1;
        return quantity * basePrice;
    }
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        String x=NumberFormat.getCurrencyInstance().format(number);
        priceTextView.setText(x);

    }
}