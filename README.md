## Journal

A utility to log journal entries that represent actions and thoughts then identify and manage your open tasks.

The intent is for this to function a little like Twitter with some workflow management thrown in.  You log actions and useful information, you can reference it and it will keep track of tasks you have open.

### Running

The API is run via `JournalApplication` through Springboot
The UI is run via journal-ui folder via npm with `npm start`

### Use cases 

Use case 1: A colleague asks you about work item X, you draw a blank. You can't remember every little thing you've done.
Solution: Search for entries containing "_work item X_" or "_X_".  I've worked on it before and here and the links to get me back up to speed

Use case 2 (In Progress): What are my outstanding tasks?
Solution: Look at open task list

#### Plans

- Multi user support
  - Privacy settings to allow sharing of entries
- Annotate past entries
- Search by date
- Preview formatted entry card


## Journal Server

A RESTful API for CRUD of Journal Entries in Java.  Also working on an [Open API Spec](https://github.com/rossdrew/journal/blob/main/openapi.yaml)

# `GET /entries?[contains="journal"][start=0][limit=10]`


Get a list of journal entries. Optionally 
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
      "creation": "2021-12-07T10:54:16.819+00:00",
      "id": "1642519938861"
      
    },
    {
      "body": "This is TEST journal entry No.3",
      "creation": "2021-12-07T10:54:16.830+00:00",
      "id": "1642519938862"
    },
    {
      "body": "This is TEST journal entry No.4",
      "creation": "2021-12-07T10:54:16.842+00:00",
      "id": "1642519938863"
    },
    {
      "body": "This is TEST journal entry No.5",
      "creation": "2021-12-07T10:54:16.853+00:00",
      "id": "1642519938864"
    },
    {
      "body": "This is TEST journal entry No.6",
      "creation": "2021-12-07T10:54:16.863+00:00",
      "id": "1642519938865"
    }
  ]
}
```

# `GET /entries/{entry_id}`

Get a specific entry:

```json
{
  "body": "This is TEST journal entry No.2",
  "creation": "2021-12-07T10:54:16.819+00:00",
  "id": "1642519938884"
}
```

# `POST /entries/append`

Append a new journal entry.  Creation field will be added automatically and any value provided for `created` will be ignored.

```json
{
  "body": "text body of journal entry",
  "creation": "2022-01-18T16:23:31.146+00:00",
  "id": "1642523011146"
}
```

## Journal UI (SPA)

REACT based Single Page Application that allows viewing/adding journal entries and filtering what is requested and shown based on body content.

![Current SPA UI](https://github.com/rossdrew/journal/blob/main/Journal%20v1.1.png)

## Journal UI (Fallback)

A basic HTML page that allows viewing (via refresh) entries and adding entries with a form.

# Thanks

Some collegues have helped me while learning React & front-end development.  So thanks [Al McKinlay](https://github.com/McInkay) and [Tim James](https://stackoverflow.com/users/177988/tim-b-james?tab=profile) 
