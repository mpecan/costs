import React from 'react';
import {mount} from 'enzyme';
import MainContainer from './MainContainer';
import mockresponse from './testSupport/mockresponse';

describe('testing the MainContainer', () => {
    it('renders the DataCards if screen size is lower than middle', () => {
        const body = JSON.stringify(mockresponse);
        fetch.mockResponseOnce(body);
        const wrapper = mount(<MainContainer />);
        console.log(wrapper.html());
    });
});

