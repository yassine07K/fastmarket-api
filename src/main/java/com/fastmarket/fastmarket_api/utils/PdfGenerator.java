package com.fastmarket.fastmarket_api.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.LigneCommande;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static ByteArrayInputStream generateCommandePdf(Commande commande) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Confirmation de votre commande"));
            document.add(new Paragraph("Commande ID : " + commande.getId()));
            document.add(new Paragraph("Magasin : " + commande.getMagasin().getNom()));
            document.add(new Paragraph("Créneau : " + commande.getCreneau().getHeureDebut() + " - " + commande.getCreneau().getHeureFin()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Produit");
            table.addCell("Quantité");
            table.addCell("Prix");

            for (LigneCommande ligne : commande.getLignesCommande()) {
                table.addCell(ligne.getProduit().getLibelle());
                table.addCell(ligne.getQuantite().toString());
                table.addCell(ligne.getProduit().getPrixUnitaire() + " €");
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}