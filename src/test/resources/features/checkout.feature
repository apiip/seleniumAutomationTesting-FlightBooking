@checkout
Feature: Flight Checkout

  Background:
    Given user on flight tab

  Scenario Outline: User Checkout with single trip onboadring
    When user had input flight detail
    And user sets passengers adult <adults> child <children> infant <infants>
    And user sets flight category <flightCategory>
    And user search and select cheapest flight of <airline>
    And user fill data on booking information page
    Then user validate the detail order on payment check page

Examples:
    | adults | children | infants | flightCategory | airline            |
    | 1      | 0        | 0       | "economy"      | "Indonesia AirAsia"|
    | 2      | 1        | 1       | "economy"      | "Indonesia AirAsia"|
    | 1      | 2        | 1       | "business"     | "Garuda Indonesia" |

