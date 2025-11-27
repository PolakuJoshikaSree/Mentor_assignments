package com.trademaxpro.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;

// summary of complete Portfolio.
@Data
@AllArgsConstructor
public class PortfolioSummary {

    private List<PortfolioViewItem> items; // each stock view
    private double totalInvested;          
    private double totalCurrent;          
    private double overallReturnPercent;   // P/L %
}
