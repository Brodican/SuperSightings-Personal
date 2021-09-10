# SuperSightings Personal
 An application to track superhero sightings. Please see implementation plan and release notes for more information.
 
 # **Release Notes**

### Functional Features

You are presented with a home page upon starting the application with a number of buttons and a list of the most recently viewed sightings, alongside some buttons.

**The Home Page:**

- There is a title stating the name of the app (&quot;Superhero Sightings&quot;) which is found on all pages.
- The Locations, Supers, Sightings, and Organizations buttons lead to the respective pages of those entities when clicked. This is the navigation bar, and it is present on a number of pages.
- There is a short explanation of the application under the navigation bar.
- There is a list of the 10 most recent superhero sightings below the explanation, with the ID, Date, and Location of the Sighting, and an option to view the details of a sighting on a separate page.

**The Locations Page:**

- The locations page has a button to go back to the home page under its title (&quot;Superhero Sightings&quot;). In fact, all pages other than the home page have this button.
- It has the navigation bar.
- It has a form below the navigation bar for taking all the data of a Location, and adding it to the database.
- It has a list of all locations below the form, with all the details of each location, an &quot;edit&quot; button for each location to go to its edit page, and a &quot;delete&quot; button to delete the location (which leads to a delete confirmation page).

**The Supers Page:**

- The Supers page has a home button.
- It has the navigation bar.
- It has a form below the navigation bar for taking all the data of a super, and adding it to the database if the data is valid and the &quot;Add Supe&quot; button below the form is clicked.
- It has a list of all supers below the form, with all the details of each super, an &quot;edit&quot; button for each super to go to its edit page, and a &quot;delete&quot; button to delete the super (which leads to a delete confirmation page).

**The Sightings Page:**

- The Sightings page has a home button.
- It has the navigation bar.
- It has a form below the navigation bar for taking all the data of an sighting, and adding it to the database if the data is valid and the &quot;Add Sighting&quot; button below the form is clicked.
- It has a list of all sightings below the form, with all the details of each sighting (apart from the supers and location associated with it), an &quot;edit&quot; button for each sighting to go to its edit page, a &quot;details&quot; button which leads to the details page for the sighting in the row, and a &quot;delete&quot; button to delete the sighting (which leads to a delete confirmation page).

**The Organizations Page:**

- The Organizations page has a home button.
- It has the navigation bar.
- It has a form below the navigation bar for taking all the data of an organization, and adding it to the database if the data is valid and the &quot;Add Organization&quot; button below the form is clicked.
- It has a list of all organizations below the form, with all the details of each organization (apart from the supers associated with it), an &quot;edit&quot; button for each organization to go to its edit page, a &quot;details&quot; button which leads to the details page for the organization in the row, and a &quot;delete&quot; button to delete the organization (which leads to a delete confirmation page).

**The Edit Page**

- The edit pages each have a home button.
- They each have the navigation bar.
- They each have a form below the navigation bar for taking all the data of their respective entity (this is filled with data already stored in the selected entity), and adding it to the database if the &quot;Update [Entity]&quot; button is clicked. The entered data overwrites the data that is currently stored in the database for the tuple with the id that was picked in the respective list of entities. The respective entity list is returned to once the button is clicked.
- Below the form, they each have a &quot;Cancel&quot; button – this returns the user to the respective entity list page without updating the chosen entity.

**The Details Page**

- The details pages each have a home button.
- They each have the navigation bar.
- The details pages have all the details for their respective entities (associated supers for organizations, and seen supers plus a location for sightings). The respective entity is the entity chosen (whose &quot;edit&quot; button was clicked on) in the list of entities (which could be organizations or sightings, as both of these entities have details that can&#39;t be viewed in their lists).
- Below the details, they each have a &quot;Cancel&quot; button – this returns the user to the respective entity list page without updating the chosen entity.

**The Delete Confirmation Page**

- The delete confirmation pages each have a home button.
- They each have the navigation bar.
- Below the navigation bar, they each have a &quot;Confirm&quot; button and a &quot;Cancel&quot; button – the confirm button deletes the previously selected entity from its respective table (it deleted the tuple of the table with an ID equivalent to the ID of the entity selected in the list of the previous page), while the cancel button returns the user to the respective entity list page without deleting the chosen entity.

### Non-Functional Features

- All added and edited data is saved in a local database.
- Data is read from the local database.
- Full input validation is performed when attempting to add an entity.
- The website is structured and coloured in a pleasant manner.
