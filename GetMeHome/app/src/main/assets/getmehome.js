function destinationPageLoad()
{
    document.getElementById("destinationStreetAddress").value = Android.getDestinationStreetAddress();
    document.getElementById("destinationCity").value = Android.getDestinationCity();
    document.getElementById("destinationState").value = Android.getDestinationState();
    document.getElementById("destinationZipCode").value = Android.getDestinationZipCode();
}

function mainPageLoad()
{
    if (Android.isDestinationSet()) {
        document.getElementById("menuItemTakeMeHome").style.display = "block";
    } else {
        document.getElementById("menuItemTakeMeHome").style.display = "none";
    }
}

function saveDestination()
{
    Android.setDestinationStreetAddress(document.getElementById("destinationStreetAddress").value);
    Android.setDestinationCity(document.getElementById("destinationCity").value);
    Android.setDestinationState(document.getElementById("destinationState").value);
    Android.setDestinationZipCode(document.getElementById("destinationZipCode").value);

    Android.showToast("Destination Saved");
}

function startAndroidMapApplication()
{
    Android.loadMapApplication();
}

