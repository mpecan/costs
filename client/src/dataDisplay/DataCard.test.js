import React from 'react';
import {render} from 'react-testing-library';
import DataCard from './DataCard';
import columns from '../columns'

it('renders the expected data', () => {
    const columnName = 'totalDischarges';
    const { getByText } = render(<DataCard columnNames={[columnName]} element={{[columnName]: "Value"}}/>);
    expect(getByText("Value")).toBeInTheDocument();
    expect(getByText(columns[columnName])).toBeInTheDocument();
});
