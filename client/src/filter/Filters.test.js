import React from 'react';
import {mount} from 'enzyme';
import Filters from './Filters';

describe('communicates information about the filter that is being passed trough the changeFilters function', () => {
    it("handles min_discharges being set to 1200", (done) => {
        const wrapper = mount(<Filters changeFilters={(value) => {
            expect(value).toBe("min_discharges=1200");
            done();
        }}/>);
        wrapper.find('input#minDischarges').simulate('change', {target: {value: "1200"}});
        wrapper.find('button[type="submit"]').simulate("submit", {target: {}});
    });

    it("handles all filters being set", (done) => {
        const wrapper = mount(<Filters changeFilters={(value) => {
            expect(value).toContain("min_discharges=1200");
            expect(value).toContain("max_discharges=2200");
            expect(value).toContain("min_average_covered_charges=100.34");
            expect(value).toContain("max_average_covered_charges=300");
            expect(value).toContain("min_average_medicare_payments=50");
            expect(value).toContain("max_average_medicare_payments=3500");
            expect(value).toContain("state=AZ");
            done();
        }}/>);
        wrapper.find('input#minDischarges').simulate('change', {target: {value: "1200"}});
        wrapper.find('input#maxDischarges').simulate('change', {target: {value: "2200"}});
        wrapper.find('input#minAverageCoveredCharges').simulate('change', {target: {value: "100.34"}});
        wrapper.find('input#maxAverageCoveredCharges').simulate('change', {target: {value: "300"}});
        wrapper.find('input#minAverageMedicarePayments').simulate('change', {target: {value: "50"}});
        wrapper.find('input#maxAverageMedicarePayments').simulate('change', {target: {value: "3500"}});
        wrapper.find('input#state').simulate('change', {target: {value: "AZ"}});
        wrapper.find('button[type="submit"]').simulate("submit", {target: {}});
    });

});
