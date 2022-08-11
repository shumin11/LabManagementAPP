
#  My Personal Project - ***Lab Management***

## Introduction

It is an application helping people working in biology labs to manage their lab supplies.

Normally, for doing experiments in the biology lab, there are many chemicals and machines needed. And people in the lab
are sharing the same lab supplies and machines. This application will help them coordinate with each other, and
to keep track all key details of lab supplies, like **location**, **quantities**, **vendors**, etc. I have worked 
in wet lab over ten years, so I would like to design this application to help researchers in the lab to save time 
looking for chemicals, waiting for supplies to order, and communicating well with people in the same lab.  

### *User Stories*
- As a user, I want to be able to create a new type and add it to a list of types 
  - types: CHEMICAL, GENERAL SUPPLY, KITS, etc.
- As a user, I want to be able to select a type and add a new item to that type
- As a user, I want to be able to delete a type from the list
- As a user, I want to be able to delete an item from that type
- As a user, I want to be able to view the list of types or the list of items on selected type
- As a user, I want to be able to get details for selected item
   - such as AMOUNT, LOCATION, VENDOR, UPDATED, etc.
- As a user, I want to be able to save all lab inventory information to file
- As a user, I want to be able to load previous saved information from file 

#  Instructions for Grader

- You can generate the first required event by clicking the button labelled "ADD"
- You can generate the second required event by clicking the button labelled "DELETE"
- You can locate my visual component on the starting login page 
  (only username: shumin, password: 123456 will bring you to main page)
- You can save the state of my application by clicking button labelled "SAVE"
- You can reload the state of my application by selecting the menu item "File" then "load"

# Phase 4: Task 2
Wed Aug 10 20:41:19 PDT 2022
Item (named: Tips , amount: 200 , location: RoomA , vendor: Fisher , updated: 2022-08-10 , toOrder: false) ADDED to the type of General Supply


Wed Aug 10 20:41:29 PDT 2022
Item (named: Gloves , amount: 100 , location: RoomA , vendor: Fisher , updated: 2022-08-10 , toOrder: true) ADDED to the type of General Supply


Wed Aug 10 20:41:42 PDT 2022
Item (named: Flask , amount: 30 , location: RoomA , vendor: Fisher , updated: 2022-08-10 , toOrder: false) ADDED to the type of General Supply


Wed Aug 10 20:41:59 PDT 2022
Item (named: Agar , amount: 2 , location: RoomB , vendor: Fisher , updated: 2022-08-10 , toOrder: false) ADDED to the type of Chemical


Wed Aug 10 20:42:03 PDT 2022
Item (named: Flask , amount: 30 , location: RoomA , vendor: Fisher , updated: 2022-08-10 , toOrder: false) REMOVED from the type of General Supply

# Phase 4: Task 3
- Add an abstract class for LabInventoryAPP and LabInventoryAPPUI, as they share similar methods, so I can put their shared
  methods in the abstract class and let both of them extend it.

- Abstract duplicated code into Methods in the classes, especially for the LabInventoryAPP and LabInventoryAPPUI




