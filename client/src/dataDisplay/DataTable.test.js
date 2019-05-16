import React from 'react';
import {render} from 'react-testing-library';
import DataTable from './DataTable';
import columns from '../columns'

it('renders the expected data', () => {
    const columnName = 'totalDischarges';
    const { getByText } = render(<DataTable columnNames={[columnName]} data={[{id: 1, [columnName]: "Value"}, {id: 2, [columnName]: "Value 2"}]}/>);
    expect(getByText("Value")).toBeInTheDocument();
    expect(getByText("Value 2")).toBeInTheDocument();
    expect(getByText(columns[columnName])).toBeInTheDocument();
});
