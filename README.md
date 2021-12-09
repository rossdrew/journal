## Journal

An app to track your day.

## Journal Server

A RESTful API for CRUD of Journal Entries in Java.

`GET /entries?[contains="journal"][start=0][limit=10]`

# Get a list of journal entries. Optionally 
 - filtered on entries in which their `body` `contains` a given string.
 - returning only `limit` number of entries
 - returning only entries from `start` index

Combining `start` and `limit` therefore gives us paging functionality.  As a result the data is returned in a paging header (`size`, `limit`, `startIndex`) with the data (entires) found under `data`:

```json
{
  "size": 25,
  "limit": 5,
  "startIndex": 2,
  "data": [
    {
      "body": "This is TEST journal entry No.2",
      "creation": "2021-12-07T10:54:16.819+00:00"
    },
    {
      "body": "This is TEST journal entry No.3",
      "creation": "2021-12-07T10:54:16.830+00:00"
    },
    {
      "body": "This is TEST journal entry No.4",
      "creation": "2021-12-07T10:54:16.842+00:00"
    },
    {
      "body": "This is TEST journal entry No.5",
      "creation": "2021-12-07T10:54:16.853+00:00"
    },
    {
      "body": "This is TEST journal entry No.6",
      "creation": "2021-12-07T10:54:16.863+00:00"
    }
  ]
}
```

# `POST /entries/append`

Append a new journal entry.  Creation field will be added automatically and any value provided for `created` will be ignored.

```json
{
 "body": "text body of journal entry",
}
```

## Journal UI (SPA)

REACT based Single Page Application that allows viewing/adding journal entries and filtering what is requested and shown based on body content.

![Current SPA UI](https://github.com/rossdrew/journal/blob/main/Journal%20v1.1.png)

## Journal UI (Fallback)

A basic HTML page that allows viewing (via refresh) entries and adding entries with a form.

# Thanks

Some collegues have helped me while learning React & front-end development.  So thanks [Al McKinlay](https://github.com/McInkay) and [Tim James](https://stackoverflow.com/users/177988/tim-b-james?tab=profile) 
