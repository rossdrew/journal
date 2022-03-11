import { render, screen } from '@testing-library/react';
import JournalEntries from "./journalEntries";

test('Renders empty entries with last updated banner', () => {
    render(<JournalEntries />);
    const lastUpdatedText = screen.getByText("Last Updated:", {exact: false})
    expect(lastUpdatedText).toBeInTheDocument();
});


//Test that when the server returns an item, an item is shown