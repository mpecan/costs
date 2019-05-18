import React from 'react';
import {mount} from 'enzyme';
import MainContainer from './MainContainer';
import mockresponse from './testSupport/mockresponse';

describe('testing the MainContainer', () => {
    it('renders the component without breaking', () => {
        const body = JSON.stringify(mockresponse);
        fetch.mockResponseOnce(body);
        mount(<MainContainer />);
    });
});

