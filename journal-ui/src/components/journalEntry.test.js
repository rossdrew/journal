import React from 'react';
import { render } from '@testing-library/react';
import JournalEntry from './journalEntry';

describe('JournalEntry', () => {
    let mockEntry;

    beforeEach(() => {
        mockEntry = {
            creation: new Date().toISOString(),
            body: 'This is the body of a test entry'
        };
    });

    it('renders without crashing', () => {
        render(<JournalEntry entry={mockEntry} />);
    });

    it('renders the correct date', () => {
        const { getByText } = render(<JournalEntry entry={mockEntry} />);
        const dateElement = getByText(new Date(mockEntry.creation).toLocaleString());

        expect(dateElement).toBeInTheDocument();
    });

    it('renders the correct body', () => {
        const { getByText } = render(<JournalEntry entry={mockEntry} />);
        const bodyElement = getByText('This is the body of a test entry');

        expect(bodyElement).toBeInTheDocument();
    });
});