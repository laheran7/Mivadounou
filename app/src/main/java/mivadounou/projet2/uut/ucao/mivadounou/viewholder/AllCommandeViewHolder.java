package mivadounou.projet2.uut.ucao.mivadounou.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mivadounou.projet2.uut.ucao.mivadounou.R;
import mivadounou.projet2.uut.ucao.mivadounou.models.CommandeMenu;

/**
 * Created by leinad on 7/13/17.
 */

public class AllCommandeViewHolder extends RecyclerView.ViewHolder {

    private static final String TIME_PATTERN = "HH:mm";

    private TextView cRestauAndMenuTitle;
    private TextView cQuantityAndPrice;
    private TextView cTotalPrice;
    private TextView cDateAndEndTime;
    private TextView cPastTime;
    private TextView cStatus;
    private TextView commandeTitle;

    private ImageView menuIconTypeImageView;
    private ImageView menuIconStatusImageView;

    private Button cancellCommande;

    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

    public AllCommandeViewHolder(View itemView) {

        super(itemView);

        cRestauAndMenuTitle = (TextView) itemView.findViewById(R.id.commande_menu_and_restau_name);
        cQuantityAndPrice = (TextView) itemView.findViewById(R.id.commande_quantity_and_unit_price);
        cTotalPrice = (TextView) itemView.findViewById(R.id.commande_total_price);
        cDateAndEndTime = (TextView) itemView.findViewById(R.id.commande_end_date_and_time);
        cPastTime = (TextView) itemView.findViewById(R.id.commande_past_time);
        cStatus = (TextView) itemView.findViewById(R.id.commande_status);
        commandeTitle = (TextView) itemView.findViewById(R.id.commande_title);

        menuIconTypeImageView = (ImageView) itemView.findViewById(R.id.menu_icon_type);
        menuIconStatusImageView = (ImageView) itemView.findViewById(R.id.menu_icon_status);

        cancellCommande = (Button) itemView.findViewById(R.id.cancel_commande);

    }

    public void bindToPost(CommandeMenu commandeMenu, View.OnClickListener onClickListener) {

        switch (commandeMenu.getStatus()) {
            case CommandeMenu.COMMANDE_SENT:
                cStatus.setText("Status : Envoyeé");

                menuIconStatusImageView.setImageResource(R.drawable.ic_lens_black_24dp);

                cancellCommande.setEnabled(true);
                break;

            case CommandeMenu.COMMANDE_ACCEPT:
                cStatus.setText("Status : Acceptée");

                menuIconStatusImageView.setImageResource(R.drawable.ic_lens_yellow_24dp);

                cancellCommande.setEnabled(false);
                cancellCommande.setVisibility(View.GONE);

                break;

            case CommandeMenu.COMMANDE_DONE:
                cStatus.setText("Status : Effectuée");

                commandeTitle.setText("Vouz aviez commandez");

                menuIconStatusImageView.setImageResource(R.drawable.ic_lens_green_24dp);

                cancellCommande.setEnabled(false);
                cancellCommande.setVisibility(View.GONE);
                break;
        }

        switch (commandeMenu.getMenuType()) {
            case "Plat Africain":
                menuIconTypeImageView.setImageResource(R.drawable.ic_restaurant);
                break;
            case "Pizza":
                menuIconTypeImageView.setImageResource(R.drawable.ic_pie);
                break;
            case "Burger":
                menuIconTypeImageView.setImageResource(R.drawable.ic_fast_food);
                break;
        }

        cRestauAndMenuTitle.setText(commandeMenu.getMenuTitle() + " à " + commandeMenu.getRestauTitle());
        cQuantityAndPrice.setText("Quantité : " + commandeMenu.getQuantity() + ", Prix unitaire : " + commandeMenu.getUnitPrice() + " FCFA");
        cTotalPrice.setText("Prix totale : " + commandeMenu.getTotalPrice() + " FCFA");

        Calendar endAtCalendar = Calendar.getInstance();
        endAtCalendar.setTimeInMillis(commandeMenu.getEndAtTimestamp());

        cDateAndEndTime.setText("Pour le " + dateFormat.format(endAtCalendar.getTime()) + " à " + timeFormat.format(endAtCalendar.getTime()));

        Calendar createAtCalendar = Calendar.getInstance();
        createAtCalendar.setTimeInMillis(commandeMenu.getCreateAtTimestamp());

        PrettyTime prettyTime = new PrettyTime(new Locale("fr"));

        cPastTime.setText("Envoyée " + prettyTime.format(createAtCalendar.getTime()));

        cancellCommande.setOnClickListener(onClickListener);
    }
}
