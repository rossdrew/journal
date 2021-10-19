## Journal

An app to track your day.

## Journal Server

A RESTful API for CRUD of Journal Entries in Java.

`GET /entries`

Get a list of journal entries

```json
{
 body: <text body of journal entry,
 creation: <date and time entry was created>
}
```

`POST /entries/append`

Append a new journal entry.  Creation field will be added automatically and any value provided for `created` will be ignored.

```json
{
 body: <text body of journal entry,
}
```

## Journal UI (SPA)

REACT based Single Page Application that allows viewing and adding journal entries.

## Journal UI (Fallback)

A basic HTML page that allows viewing (via refresh) entries and adding entries with a form.
