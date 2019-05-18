import React from 'react';
import {mount} from 'enzyme';
import DataDisplay from './DataDisplay';
import {setDefaultBreakpoints, BreakpointProvider} from 'react-socks';

describe('testing the responsive nature of the component', () => {

    it('renders the DataCards if screen size is lower than middle', () => {
        setDefaultBreakpoints([
            {small: 768},
            {medium: 1024},
            {large: 2048}
        ]);
        const columnName = 'totalDischarges';

        const data = [
            {id: 1, [columnName]: "Value"},
            {id: 2, [columnName]: "Value 2"}
        ];
        const wrapper = mount(<BreakpointProvider>
                <DataDisplay columnNames={[columnName]} data={data}/>
            </BreakpointProvider>
        );
        expect(wrapper.find('div.DataCard-card-1').length).toBe(2);
    });

    it('renders the DataTable if screen size is over than middle', () => {
        setDefaultBreakpoints([
            {small: 333},
            {medium: 768},
            {large: 1024}
        ]);
        const columnName = 'totalDischarges';

        const data = [
            {id: 1, [columnName]: "Value"},
            {id: 2, [columnName]: "Value 2"}
        ];
        const wrapper = mount(<BreakpointProvider>
                <DataDisplay columnNames={[columnName]} data={data}/>
            </BreakpointProvider>
        );
        expect(wrapper.find('table').length).toBe(1);
    });
})

